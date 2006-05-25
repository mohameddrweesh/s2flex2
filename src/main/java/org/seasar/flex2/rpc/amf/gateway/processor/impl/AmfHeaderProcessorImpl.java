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

import org.seasar.flex2.rpc.amf.data.AmfHeader;
import org.seasar.flex2.rpc.amf.data.AmfHeaderConstants;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.factory.AmfHeaderFactory;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfHeaderProcessor;
import org.seasar.flex2.rpc.gateway.session.SessionDecorator;
import org.seasar.flex2.rpc.gateway.session.util.HttpSessionUtil;

public class AmfHeaderProcessorImpl implements AmfHeaderProcessor {

    private AmfHeaderFactory headerFactory;

    private SessionDecorator sessionDecorator;
    
    public AmfHeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    public void processRequest(HttpServletRequest request,
            AmfMessage requestMessage) {
    }

    public void processResponse(HttpServletRequest request,
            AmfMessage responseMessage) {

        if (!request.isRequestedSessionIdValid()) {
            setUrlSessionId(request, responseMessage);
        }
    }
    
    public void setHeaderFactory(AmfHeaderFactory headerFactory) {
        this.headerFactory = headerFactory;
    }

    public void setSessionDecorator(SessionDecorator sessionDecorator) {
        this.sessionDecorator = sessionDecorator;
    }

    protected void addHeader(AmfMessage responseMessage, String headerName,
            String data) {
        AmfHeader header = headerFactory.createHeader(headerName, data);
        responseMessage.addHeader(header);
    }

    private void setUrlSessionId(HttpServletRequest request,
            AmfMessage responseMessage) {
        String sessionId = HttpSessionUtil.getSessionId(request);
        if (sessionId != null && sessionId.length() > 0) {
            sessionId = sessionDecorator.formatSessionId(sessionId);
            addHeader(responseMessage, AmfHeaderConstants.APPEND_TO_GATEWAYURL,
                    sessionId);
        }
    }
}