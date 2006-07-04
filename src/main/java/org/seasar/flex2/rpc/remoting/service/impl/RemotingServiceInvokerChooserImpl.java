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
package org.seasar.flex2.rpc.remoting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvokerChooser;
import org.seasar.flex2.rpc.remoting.service.exception.InvokerNotFoundRuntimeException;

public class RemotingServiceInvokerChooserImpl implements RemotingServiceInvokerChooser {

    private final List invokers;

    public RemotingServiceInvokerChooserImpl() {
        invokers = new ArrayList();
    }

    public void addInvoker(RemotingServiceInvoker invoker) {
        invokers.add(invoker);
    }

    public RemotingServiceInvoker chooseInvoker( final MessageBody requestBody) {
        RemotingServiceInvoker invoker;
        for (int i = 0; i < invokers.size(); ++i) {
            invoker = (RemotingServiceInvoker) invokers.get(i);
            if (invoker.supports(requestBody.getServiceName(), requestBody
                    .getServiceMethodName(), requestBody.getArgs())) {
                return invoker;
            }
        }
        throw new InvokerNotFoundRuntimeException(requestBody.getServiceName());
    }
}