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
package org.seasar.flex2.rpc.remoting.message.data.processor.impl;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.factory.ErrorInfoFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageFactory;
import org.seasar.flex2.rpc.remoting.message.data.processor.MessageBodyProcessor;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvokerChooser;

public class MessageBodyProcessorImpl implements MessageBodyProcessor {

    private static final String RESPONSE_RESULT = "/onResult";

    private static final String RESPONSE_STATUS = "/onStatus";

    private MessageBodyFactory bodyFactory;

    private ErrorInfoFactory errorFactory;

    private MessageFactory messageFactory;

    private RemotingServiceInvokerChooser serviceInvokerChooser;

    public MessageBodyFactory getBodyFactory() {
        return bodyFactory;
    }

    public ErrorInfoFactory getErrorFactory() {
        return errorFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public RemotingServiceInvokerChooser getServiceInvokerChooser() {
        return serviceInvokerChooser;
    }

    public Message process(Message requestMessage) {
        Message responseMessage = messageFactory.createMessage(requestMessage
                .getVersion());

        for (int i = 0; i < requestMessage.getBodySize(); ++i) {
            MessageBody requestBody = requestMessage.getBody(i);
            MessageBody responseBody = processBody(requestBody);
            responseMessage.addBody(responseBody);
        }
        return responseMessage;
    }

    public void setBodyFactory(MessageBodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public void setErrorFactory(ErrorInfoFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setServiceInvokerChooser(
            RemotingServiceInvokerChooser serviceInvokerChooser) {
        this.serviceInvokerChooser = serviceInvokerChooser;
    }

    private final MessageBody createResponseBody(final String target,
            final Object result) {
        return bodyFactory.createBody(target, null, result);
    }

    protected final MessageBody processBody(final MessageBody requestBody) {
        Object result;
        String responseTarget;
        try {
            final RemotingServiceInvoker invoker = serviceInvokerChooser
                    .chooseInvoker(requestBody);

            result = invoker.invoke(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs());

            responseTarget = requestBody.getResponse() + RESPONSE_RESULT;
        } catch (Throwable throwable) {
            result = errorFactory.createErrorInfo(throwable);
            responseTarget = requestBody.getResponse() + RESPONSE_STATUS;
        }

        return createResponseBody(responseTarget, result);
    }
}