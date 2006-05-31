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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.seasar.flex2.message.format.amf.AmfHeaderConstants;
import org.seasar.flex2.message.format.amf.processor.AmfMessageProcessor;
import org.seasar.flex2.rpc.gateway.session.SessionDecorator;
import org.seasar.flex2.rpc.gateway.session.util.HttpSessionUtil;
import org.seasar.flex2.rpc.remoting.processor.RemotingMessageProcessor;

public class AmfMessageRequestProcessorImpl implements RemotingMessageProcessor {

    private AmfMessageProcessor processor;
    
    private SessionDecorator sessionDecorator;

    public AmfMessageProcessor getProcessor() {
        return processor;
    }

    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            DataInputStream inputStream = getRequestInputStream(request);
            DataOutputStream outputStream = getRequestOutputStream(response);

            Map headers = createMessageHeaders(request);
            processor.process(inputStream,outputStream,headers);
            
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

    public void setProcessor(AmfMessageProcessor processor) {
        this.processor = processor;
    }
    
    public void setSessionDecorator(SessionDecorator sessionDecorator) {
        this.sessionDecorator = sessionDecorator;
    }

    private final Map createMessageHeaders(HttpServletRequest request) {
      Map headers = new HashMap();
      if (!request.isRequestedSessionIdValid()) {
          String sessionId = HttpSessionUtil.getSessionId(request,true);
          sessionId = sessionDecorator.formatSessionId(sessionId);
          headers.put(AmfHeaderConstants.APPEND_TO_GATEWAYURL, sessionId);
      }
      
      return headers;
    }

    private final DataInputStream getRequestInputStream( final HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        return new DataInputStream(inputStream);
    }
    
    private final DataOutputStream getRequestOutputStream( final HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        if (!(outputStream instanceof BufferedOutputStream)) {
            outputStream = new BufferedOutputStream(outputStream);
        }
        return new DataOutputStream(outputStream );
    }
}