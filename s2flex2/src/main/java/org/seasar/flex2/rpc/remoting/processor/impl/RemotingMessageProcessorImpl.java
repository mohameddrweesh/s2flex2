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
package org.seasar.flex2.rpc.remoting.processor.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.flex2.rpc.remoting.message.data.processor.MessageProcessor;
import org.seasar.flex2.rpc.remoting.processor.RemotingMessageHeaderCreator;
import org.seasar.flex2.rpc.remoting.processor.RemotingMessageProcessor;
import org.seasar.flex2.util.io.InputStreamUtil;
import org.seasar.flex2.util.io.OutputStreamUtil;

public class RemotingMessageProcessorImpl implements RemotingMessageProcessor {

    private MessageProcessor messageProcessor;

    private final List headerCreators;
    
    public RemotingMessageProcessorImpl(){
        headerCreators = new ArrayList();
    }

    public void addHeaderCreator( RemotingMessageHeaderCreator creator ){
        headerCreators.add(creator);
    }

    public MessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void process(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException,
            ServletException {

        try {
            final DataInputStream inputStream = (DataInputStream) InputStreamUtil
                    .toBufferedDataInputStream(request.getInputStream());
            final DataOutputStream outputStream = (DataOutputStream) OutputStreamUtil
                    .toBufferedDataOutputStream(response.getOutputStream());

            final List headers = createMessageHeaders(request);
            messageProcessor.process(inputStream, outputStream, headers);

            response.setContentLength(outputStream.size());
            outputStream.flush();

        } catch (Throwable throwable) {
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

    public void setMessageProcessor(MessageProcessor processor) {
        this.messageProcessor = processor;
    }
    
    private final List createMessageHeaders(final HttpServletRequest request) {
        List headers = new ArrayList();
        if (headerCreators.size() > 0) {
            RemotingMessageHeaderCreator processor;
            for (Iterator creatorIt = headerCreators.iterator(); creatorIt.hasNext();) {
                processor = (RemotingMessageHeaderCreator) creatorIt.next();
                Object header = processor.createHeader(request);
                if( header != null ){
                    headers.add( header );
                }
            }
        }
        return headers;
    }
}