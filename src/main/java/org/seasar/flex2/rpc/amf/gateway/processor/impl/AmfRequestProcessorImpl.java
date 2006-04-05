/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.flex2.rpc.amf.gateway.processor.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;
import org.seasar.flex2.rpc.amf.io.AmfReader;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.io.impl.AmfReaderImpl;
import org.seasar.flex2.rpc.amf.io.impl.AmfWriterImpl;
import org.seasar.flex2.rpc.amf.util.AmfMessageUtil;
import org.seasar.flex2.rpc.gateway.RequestProcessor;
import org.seasar.flex2.rpc.gateway.invoker.ServiceInvoker;
import org.seasar.flex2.rpc.gateway.invoker.exception.InvokerNotFoundRuntimeException;
import org.seasar.framework.log.Logger;

public class AmfRequestProcessorImpl implements RequestProcessor {

    private List invokers = new ArrayList();

    public void addInvoker(ServiceInvoker invoker) {
        invokers.add(invoker);
    }

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            AmfMessage requestMessage = readMessage(request);
            AmfMessage responseMessage = processMessage(request, requestMessage);
            writeMessage(response, responseMessage);
        } catch (Throwable t) {
            Logger.getLogger(AmfRequestProcessorImpl.class).log(t);
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else if (t instanceof Error) {
                throw (Error) t;
            } else if (t instanceof IOException) {
                throw (IOException) t;
            } else if (t instanceof ServletException) {
                throw (ServletException) t;
            } else {
                throw new ServletException(t);
            }
        }
    }

    protected AmfMessage readMessage(HttpServletRequest request)
            throws IOException, ServletException {

        InputStream is = request.getInputStream();
        if (!(is instanceof BufferedInputStream)) {
            is = new BufferedInputStream(is);
        }
        DataInputStream inputStream = new DataInputStream(is);
        AmfReader reader = createReader(inputStream);
        return reader.read();
    }

    protected AmfReader createReader(DataInputStream in) {
        return new AmfReaderImpl(in);
    }

    protected AmfMessage processMessage(HttpServletRequest request,
            AmfMessage requestMessage) throws IOException {

        AmfMessage responseMessage = createMessage(requestMessage);

        for (int i = 0; i < requestMessage.getBodySize(); ++i) {
            AmfBody requestBody = requestMessage.getBody(i);
            AmfBody responseBody = processBody(request, requestBody);
            responseMessage.addBody(responseBody);
        }
        return responseMessage;
    }

    protected AmfMessage createMessage(AmfMessage requestMessage) {
        AmfMessage responseMessage = new AmfMessageImpl();
        responseMessage.setVersion(requestMessage.getVersion());

        return responseMessage;
    }

    protected AmfBody processBody(HttpServletRequest request,
            AmfBody requestBody) {

        try {
            ServiceInvoker invoker = chooseInvoker(requestBody);
            Object result = invoker.invoke(requestBody.getServiceName(),
                    requestBody.getServiceMethodName(), requestBody.getArgs());
            return AmfMessageUtil.createResultResponseBody(requestBody.getResponse(), result);
        } catch (Throwable t) {
            return AmfMessageUtil.createStatusResponseBody(requestBody.getResponse(), t);
        }
    }

    protected ServiceInvoker chooseInvoker(AmfBody requestBody) {
        for (int i = 0; i < invokers.size(); ++i) {
            ServiceInvoker invoker = (ServiceInvoker) invokers.get(i);
            if (invoker.supports(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs())) {
                return invoker;
            }
        }
        throw new InvokerNotFoundRuntimeException(requestBody.getServiceName());
    }

    protected void writeMessage(HttpServletResponse response,
            AmfMessage responseMessage) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);
        AmfWriter writer = createAmfWriter(responseMessage, outputStream);
        writer.write();

        response.setContentLength(baos.size());
        baos.writeTo(response.getOutputStream());
        outputStream.flush();
    }

    protected AmfWriter createAmfWriter(AmfMessage responseMessage,
            DataOutputStream outputStream) {
        AmfWriter writer = new AmfWriterImpl(outputStream, responseMessage);
        return writer;
    }
}