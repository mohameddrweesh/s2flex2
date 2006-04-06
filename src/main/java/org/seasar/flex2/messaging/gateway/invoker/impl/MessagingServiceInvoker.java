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
package org.seasar.flex2.messaging.gateway.invoker.impl;

import org.seasar.flex2.rpc.gateway.invoker.impl.S2ContainerInvoker;
import org.seasar.flex2.util.data.transfer.Storage;
import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.flex2.util.data.transfer.impl.HttpSessionDataStorage;
import org.seasar.framework.container.S2Container;

public class MessagingServiceInvoker extends S2ContainerInvoker {

    public MessagingServiceInvoker() {
    }

    public Object invoke(String serviceName, String methodName, Object[] args)
            throws Throwable {

        Object component = findComponent(serviceName);
        Storage storage = createSessionDataStorage();
        Transfer.importTo(storage, component);
        try {
            return super.invokeServiceMethod(methodName, args, component);
        } finally {
            Transfer.exportTo(storage, component);
        }
    }

    private Storage createSessionDataStorage() {
        S2Container root = getContainer().getRoot();
        Storage storage = new HttpSessionDataStorage(root.getSession());
        return storage;
    }
}