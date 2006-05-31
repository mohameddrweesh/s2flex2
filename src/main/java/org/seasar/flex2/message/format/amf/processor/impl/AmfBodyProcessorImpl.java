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
package org.seasar.flex2.message.format.amf.processor.impl;

import org.seasar.flex2.message.format.amf.data.AmfBody;
import org.seasar.flex2.message.format.amf.data.AmfMessage;
import org.seasar.flex2.message.format.amf.data.factory.AmfBodyFactory;
import org.seasar.flex2.message.format.amf.data.factory.AmfErrorFactory;
import org.seasar.flex2.message.format.amf.data.factory.AmfMessageFactory;
import org.seasar.flex2.message.format.amf.processor.AmfBodyProcessor;
import org.seasar.flex2.message.format.amf.processor.ServiceInvokerChooser;
import org.seasar.flex2.rpc.gateway.invoker.ServiceInvoker;

public class AmfBodyProcessorImpl implements AmfBodyProcessor {

    private static final String RESPONSE_RESULT = "/onResult";

    private static final String RESPONSE_STATUS = "/onStatus";

    private AmfBodyFactory bodyFactory;

    private AmfErrorFactory errorFactory;

    private AmfMessageFactory messageFactory;

    private ServiceInvokerChooser serviceInvokerChooser;

    public AmfBodyFactory getBodyFactory() {
        return bodyFactory;
    }

    public AmfErrorFactory getErrorFactory() {
        return errorFactory;
    }

    public AmfMessageFactory getMessageFactory() {
        return messageFactory;
    }

    public ServiceInvokerChooser getServiceInvokerChooser() {
        return serviceInvokerChooser;
    }

    public AmfMessage process(AmfMessage requestMessage) {
        AmfMessage responseMessage = messageFactory
                .createMessage(requestMessage.getVersion());

        for (int i = 0; i < requestMessage.getBodySize(); ++i) {
            AmfBody requestBody = requestMessage.getBody(i);
            AmfBody responseBody = processBody(requestBody);
            responseMessage.addBody(responseBody);
        }
        return responseMessage;
    }

    public void setBodyFactory(AmfBodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public void setErrorFactory(AmfErrorFactory errorFactory) {
        this.errorFactory = errorFactory;
    }

    public void setMessageFactory(AmfMessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setServiceInvokerChooser(
            ServiceInvokerChooser serviceInvokerChooser) {
        this.serviceInvokerChooser = serviceInvokerChooser;
    }

    protected final AmfBody processBody(final AmfBody requestBody) {
        Object result;
        String responseTarget;
        try {
            final ServiceInvoker invoker = serviceInvokerChooser
                    .chooseInvoker(requestBody);

            result = invoker.invoke(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs());

            responseTarget = requestBody.getResponse() + RESPONSE_RESULT;
        } catch (Throwable throwable) {
            result = errorFactory.createError(throwable);
            responseTarget = requestBody.getResponse() + RESPONSE_STATUS;
        }

        return createResponseBody(responseTarget, result);
    }

    private final AmfBody createResponseBody(final String target,
            final Object result) {
        return bodyFactory.createBody(target, null, result);
    }
}