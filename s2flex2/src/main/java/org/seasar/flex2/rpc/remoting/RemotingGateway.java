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
package org.seasar.flex2.rpc.remoting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.flex2.rpc.remoting.processor.RemotingMessageProcessor;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class RemotingGateway extends HttpServlet {

    private static final long serialVersionUID = -5871115000558595292L;

    protected RemotingMessageProcessor processor;

    public void init() throws ServletException {
        final S2Container container = SingletonS2ContainerFactory
                .getContainer().getRoot();
        processor = (RemotingMessageProcessor) container
                .getComponent(RemotingMessageProcessor.class);
    }

    public void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException,
            ServletException {
        response.getWriter().write("RemotingGateway is running on http ...");
    }

    public void doPost(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException,
            ServletException {
        if( !request.isRequestedSessionIdValid()){
            request.getSession(true);
        }
        response.setContentType(RemotingConstants.CONTENT_TYPE);
        processor.process(request, response);
    }
}