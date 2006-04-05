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

import javax.servlet.http.HttpServletRequest;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.impl.AmfHeaderImpl;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfHeaderProcessor;
import org.seasar.flex2.rpc.amf.type.AmfHeaderType;
import org.seasar.flex2.rpc.gateway.session.SessionDecorator;
import org.seasar.flex2.rpc.gateway.util.HttpSessionUtil;
import org.seasar.framework.log.Logger;

public class AmfHeaderProcessorImpl implements AmfHeaderProcessor {
    
    protected static final Logger logger = Logger.getLogger(AmfHeaderProcessorImpl.class);

    private SessionDecorator sessionDecorator;

    public void processRequest(HttpServletRequest request,
            AmfMessage requestMessage) {
    }

    public void processResponse(HttpServletRequest request,
            AmfMessage responseMessage) {

        if (!request.isRequestedSessionIdValid()) {
            setUrlSessionId(request, responseMessage);
        }
    }

    public void setSessionDecorator(SessionDecorator sessionDecorator) {
        this.sessionDecorator = sessionDecorator;
    }

    protected AmfHeaderImpl createHeader(String headerName, String data) {
        return new AmfHeaderImpl(headerName, data);
    }

    protected void addHeader(AmfMessage responseMessage, String headerName,
            String data) {
        AmfHeaderImpl header = createHeader(headerName, data);
        responseMessage.addHeader(header);
        
        logger.debug("header :" + headerName + "={" +  data + "}");
    }

    private void setUrlSessionId(HttpServletRequest request,
            AmfMessage responseMessage) {
        String sessionId = HttpSessionUtil.getSessionId(request);
        if (sessionId != null && sessionId.length() > 0) {
            sessionId = sessionDecorator.formatSessionId(sessionId);
            addHeader(responseMessage, AmfHeaderType.APPEND_TO_GATEWAYURL,
                    sessionId);
        }
    }
}