/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.rpc.remoting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.flex2.rpc.remoting.processor.RemotingMessageProcessor;
import org.seasar.flex2.util.BooleanUtil;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class RemotingGateway extends HttpServlet {

    public static final String VERSION = "1.0.2";

    /**
     * 
     */
    private static final String PARAMETER_SHOW_GET_RESPONSE = "showGetResponse";

    /**
     * 
     */
    private static final String PARAMETER_USE_SESSION = "useSession";

    /**
     * 
     */
    private static final long serialVersionUID = 4841210303706096594L;

    private boolean isShowGetResponse;

    private boolean isUseSession;

    protected RemotingMessageProcessor processor;

    public void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException,
            ServletException {
        showGetResponse(response);
    }

    public void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException,
            ServletException {
        decisionSessionMode(request);
        response.setContentType(RemotingConstants.CONTENT_TYPE);
        processor.process(request, response);
    }

    public void init() throws ServletException {
        final S2Container container = SingletonS2ContainerFactory
                .getContainer().getRoot();
        processor = (RemotingMessageProcessor) container
                .getComponent(RemotingMessageProcessor.class);
        isShowGetResponse = getInitBooleanParameter(PARAMETER_SHOW_GET_RESPONSE);
        isUseSession = getInitBooleanParameter(PARAMETER_USE_SESSION);
    }

    /**
     * @param request
     */
    private final void decisionSessionMode(final HttpServletRequest request) {
        if (isUseSession) {
            if (request.isRequestedSessionIdValid()) {
                request.getSession(true);
            }
        }
    }

    private final boolean getInitBooleanParameter(final String name) {
        return BooleanUtil.toBoolean(this.getInitParameter(name));
    }

    /**
     * @param response
     * @throws IOException
     */
    private final void showGetResponse(final HttpServletResponse response)
            throws IOException {
        if (isShowGetResponse) {
            response.getWriter().write(
                    "RemotingGateway " + VERSION + " is running ...");
        }
    }
}