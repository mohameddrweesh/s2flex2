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
package org.seasar.flex2.amf.impl;

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

import org.seasar.flex2.amf.AmfBody;
import org.seasar.flex2.amf.AmfMessage;
import org.seasar.flex2.amf.AmfReader;
import org.seasar.flex2.amf.InvokerNotFoundRuntimeException;
import org.seasar.flex2.amf.RequestProcessor;
import org.seasar.flex2.amf.ServiceInvoker;
import org.seasar.framework.log.Logger;

public class RequestProcessorImpl implements RequestProcessor {

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
			Logger.getLogger(RequestProcessorImpl.class).log(t);
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

		AmfMessage responseMessage = createMessage();
		for (int i = 0; i < requestMessage.getBodySize(); ++i) {
			AmfBody requestBody = requestMessage.getBody(i);
			AmfBody responseBody = processBody(request, requestBody);
			responseMessage.addBody(responseBody);
		}
		return responseMessage;
	}

	protected AmfMessage createMessage() {
		return new AmfMessageImpl();
	}

	protected AmfBody processBody(HttpServletRequest request,
			AmfBody requestBody) {

		try {
			ServiceInvoker invoker = chooseInvoker(requestBody);
			Object result = invoker.invoke(requestBody.getServiceName(),
					requestBody.getServiceMethodName(), requestBody.getArgs());
			String target = requestBody.getResponse() + "/onResult";
			return new AmfBodyImpl(target, "null", result);
		} catch (Throwable t) {
			String target = requestBody.getResponse() + "/onStatus";
			return new AmfBodyImpl(target, "null", new AmfErrorImpl(t));
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
		AmfWriterImpl writer = new AmfWriterImpl(outputStream, responseMessage);
		writer.write();
		response.setContentType("application/x-amf");
		response.setContentLength(baos.size());
		baos.writeTo(response.getOutputStream());
		outputStream.flush();
	}
}