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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfErrorImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;
import org.seasar.flex2.rpc.amf.gateway.processor.AmfBodyProcessor;
import org.seasar.flex2.rpc.gateway.invoker.ServiceInvoker;
import org.seasar.flex2.rpc.gateway.invoker.exception.InvokerNotFoundRuntimeException;

public class AmfBodyProcessorImpl implements AmfBodyProcessor {

    private static final String RESPONSE_RESULT = "/onResult";

    private static final String RESPONSE_STATUS = "/onStatus";
    
    private static final String RESPONSE_NULL = "null";

    private List invokers = new ArrayList();

    public void addInvoker(ServiceInvoker invoker) {
        invokers.add(invoker);
    }

    public AmfMessage process(HttpServletRequest request,
            AmfMessage requestMessage) {
        AmfMessage responseMessage = createResponseMessage(requestMessage);

        for (int i = 0; i < requestMessage.getBodySize(); ++i) {
            AmfBody requestBody = requestMessage.getBody(i);
            AmfBody responseBody = processBody(request, requestBody);
            responseMessage.addBody(responseBody);
        }
        return responseMessage;
    }

    protected ServiceInvoker chooseInvoker(AmfBody requestBody) {
        for (int i = 0; i < invokers.size(); ++i) {
            ServiceInvoker invoker = (ServiceInvoker) invokers.get(i);
            if (invoker.supports(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs())) {
                return invoker;
            }
        }
        throw new InvokerNotFoundRuntimeException(requestBody.getServiceName());
    }

    protected AmfMessage createResponseMessage(AmfMessage requestMessage) {
        AmfMessageImpl responseMessage = new AmfMessageImpl();
        responseMessage.setVersion(requestMessage.getVersion());

        return responseMessage;
    }

    protected AmfBody processBody(HttpServletRequest request,
            AmfBody requestBody) {

        try {
            ServiceInvoker invoker = chooseInvoker(requestBody);
            Object result = invoker.invoke(requestBody.getServiceName(),
                    requestBody.getServiceMethodName(), requestBody.getArgs());
            return createBody(requestBody.getResponse() + RESPONSE_RESULT,
                    result);
        } catch (Throwable t) {
            return createBody(requestBody.getResponse() + RESPONSE_STATUS,
                    new AmfErrorImpl(t));
        }
    }

    protected AmfBody createBody(String response, Object result) {
        return new AmfBodyImpl(response, RESPONSE_NULL, result);
    }
}