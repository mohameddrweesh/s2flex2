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
package org.seasar.flex2.rpc.remoting.message.processor.impl;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.factory.FaultFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageFactory;
import org.seasar.flex2.rpc.remoting.message.processor.MessageBodyProcessor;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvokerChooser;
import org.seasar.framework.log.Logger;

public class MessageBodyProcessorImpl implements MessageBodyProcessor {

    private static final Logger logger = Logger
            .getLogger(MessageBodyProcessor.class);

    private static final String RESPONSE_RESULT = "/onResult";

    private static final String RESPONSE_STATUS = "/onStatus";

    private MessageBodyFactory bodyFactory;

    private FaultFactory faultFactory;

    private MessageFactory messageFactory;

    private RemotingServiceInvokerChooser serviceInvokerChooser;

    public MessageBodyFactory getBodyFactory() {
        return bodyFactory;
    }

    public FaultFactory getFaultFactory() {
        return faultFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public RemotingServiceInvokerChooser getServiceInvokerChooser() {
        return serviceInvokerChooser;
    }

    public Message process(final Message requestMessage) {
        final Message responseMessage = messageFactory.createResponseMessage();
        requestMessage.setVersion(requestMessage.getVersion());
        for (int i = 0; i < requestMessage.getBodySize(); ++i) {
            responseMessage.addBody(processBody(requestMessage.getBody(i)));
        }
        return responseMessage;
    }

    public void setBodyFactory(final MessageBodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public void setFaultFactory(final FaultFactory faultFactory) {
        this.faultFactory = faultFactory;
    }

    public void setMessageFactory(final MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setServiceInvokerChooser(
            final RemotingServiceInvokerChooser serviceInvokerChooser) {
        this.serviceInvokerChooser = serviceInvokerChooser;
    }

    protected final MessageBody processBody(final MessageBody requestBody) {
        String responseTarget;
        Object result;
        try {
            logger.log("DFLX0101",new Object[]{requestBody.getServiceName(),requestBody.getServiceMethodName()});
            
            final RemotingServiceInvoker invoker = serviceInvokerChooser
                    .chooseInvoker(requestBody);

            result = invoker.invoke(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs());

            responseTarget = requestBody.getResponse() + RESPONSE_RESULT;
        } catch (final Throwable throwable) {
            result = faultFactory.createFault(throwable);
            responseTarget = requestBody.getResponse() + RESPONSE_STATUS;
            logger.log("EFLX0101",new Object[]{requestBody.getServiceName(),requestBody.getServiceMethodName()},throwable);
        }

        return createResponseBody(responseTarget, result);
    }

    private final MessageBody createResponseBody(final String target,
            final Object result) {
        return bodyFactory.createBody(target, null, result);
    }
}