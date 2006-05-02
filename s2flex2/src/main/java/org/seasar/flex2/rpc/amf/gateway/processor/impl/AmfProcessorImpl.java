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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfBodyProcessor;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfHeaderProcessor;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfProcessor;
import org.seasar.flex2.rpc.amf.io.reader.AmfReader;
import org.seasar.flex2.rpc.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.framework.log.Logger;

public class AmfProcessorImpl implements AmfProcessor {

    private AmfBodyProcessor bodyProcessor;

    private AmfHeaderProcessor headerProcessor;
    
    private AmfReaderFactory readerFactory;
    
    private AmfWriterFactory writerFactory;

    public AmfBodyProcessor getBodyProcessor() {
        return bodyProcessor;
    }

    public AmfHeaderProcessor getHeaderProcessor() {
        return headerProcessor;
    }

    public AmfReaderFactory getReaderFactory() {
        return readerFactory;
    }

    public AmfWriterFactory getWriterFactory() {
        return writerFactory;
    }

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            final AmfMessage requestMessage = readMessage(request);

            headerProcessor.processRequest(request, requestMessage);

            final AmfMessage responseMessage = bodyProcessor.process(request,
                    requestMessage);

            headerProcessor.processResponse(request, responseMessage);

            writeMessage(response, responseMessage);
        } catch (Throwable throwable) {
            Logger.getLogger(AmfProcessorImpl.class).log(throwable);
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            } else if (throwable instanceof Error) {
                throw (Error) throwable;
            } else if (throwable instanceof IOException) {
                throw (IOException) throwable;
            } else if (throwable instanceof ServletException) {
                throw (ServletException) throwable;
            } else {
                throw new ServletException(throwable);
            }
        }
    }

    public void setBodyProcessor(AmfBodyProcessor bodyProcessor) {
        this.bodyProcessor = bodyProcessor;
    }

    public void setHeaderProcessor(AmfHeaderProcessor headerProcessor) {
        this.headerProcessor = headerProcessor;
    }

    public void setReaderFactory(AmfReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public void setWriterFactory(AmfWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    protected AmfMessage readMessage(HttpServletRequest request)
            throws IOException, ServletException {

        InputStream is = request.getInputStream();
        if (!(is instanceof BufferedInputStream)) {
            is = new BufferedInputStream(is);
        }
        DataInputStream inputStream = new DataInputStream(is);
        AmfReader reader = readerFactory.createReader(inputStream);
        
        return reader.read();
    }

    protected void writeMessage(HttpServletResponse response,
            AmfMessage responseMessage) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);
        AmfWriter writer = writerFactory.createWriter( outputStream, responseMessage);
        writer.write();

        response.setContentLength(baos.size());
        baos.writeTo(response.getOutputStream());
        
        outputStream.flush();
    }
}