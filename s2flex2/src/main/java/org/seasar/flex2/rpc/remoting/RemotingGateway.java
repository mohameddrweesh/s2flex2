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
import org.seasar.flex2.util.type.BooleanUtil;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;

public class RemotingGateway extends HttpServlet {

    private static final long serialVersionUID = -5142496558861670626L;

    protected static final String version = "1.0.2";

    protected static final Logger logger = Logger
            .getLogger(RemotingGateway.class);

    private static final String GATEWAY_VERSION = "gatewayVersion";

    private static final String PARAMETER_SHOW_GET_RESPONSE = "showGetResponse";

    private static final String PARAMETER_USE_SESSION = "useSession";

    protected RemotingMessageProcessor processor;

    private String gatewayVersion;

    private String gatewayVersionMessage;

    private boolean isShowGetResponse;

    private boolean isUseSession;

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
        initGatewayVersionMessage();
        logger.info(gatewayVersionMessage);
    }

    private final void decisionSessionMode(final HttpServletRequest request) {
        if (isUseSession) {
            if (request.isRequestedSessionIdValid()) {
                request.getSession(true);
            }
        }
    }

    private final String getGatewayVersionParameter(final String name) {
        final String value = this.getInitParameter(name);
        return (value != null ? value : version) + " ";
    }

    private final boolean getInitParameterAsBoolean(final String name) {
        return BooleanUtil.toBoolean(this.getInitParameter(name));
    }

    private final void initGatewayVersionMessage() {
        isShowGetResponse = getInitParameterAsBoolean(PARAMETER_SHOW_GET_RESPONSE);
        isUseSession = getInitParameterAsBoolean(PARAMETER_USE_SESSION);
        gatewayVersion = getGatewayVersionParameter(GATEWAY_VERSION);
        gatewayVersionMessage = "RemotingGateway " + gatewayVersion
                + "is running ...";
    }

    private final void showGetResponse(final HttpServletResponse response)
            throws IOException {
        if (isShowGetResponse) {
            response.getWriter().write(gatewayVersionMessage);
        }
    }
}