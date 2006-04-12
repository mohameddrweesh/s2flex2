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
import org.seasar.flex2.util.transfer.Transfer;
import org.seasar.flex2.util.transfer.storage.Storage;
import org.seasar.framework.container.S2Container;

public class MessagingServiceInvoker extends S2ContainerInvoker {
    public final static String SERVICE_DATA_STORAGE = "serviceDataStorage";

    private Transfer transfer;
    
    public MessagingServiceInvoker() {
    }

    public Object invoke(String serviceName, String methodName, Object[] args)
            throws Throwable {

        Object component = findComponent(serviceName);
        Storage storage = createDataStorage();
        transfer.importToData(storage, component);
        try {
            return super.invokeServiceMethod(methodName, args, component);
        } finally {
            transfer.exportToStorage(component, storage);
        }
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    protected Storage createDataStorage() {
        S2Container root = getContainer().getRoot();

        Storage storage = (Storage) root.getComponent(SERVICE_DATA_STORAGE);
        return storage;
    }
}