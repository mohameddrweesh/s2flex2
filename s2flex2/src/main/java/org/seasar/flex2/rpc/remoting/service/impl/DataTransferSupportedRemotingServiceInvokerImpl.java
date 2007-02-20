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

import org.seasar.flex2.util.data.storage.Storage;
import org.seasar.flex2.util.data.storage.StorageLocator;
import org.seasar.flex2.util.data.transfer.Transfer;

public class DataTransferSupportedRemotingServiceInvokerImpl extends
        AbstractRemotingServiceInvokerImpl {

    public static final String SERVICE_DATA_STORAGE = "serviceDataStorage";

    private StorageLocator storageLocator;

    private Transfer transfer;

    public Object doInvoke(final Object service, final String methodName,
            final Object[] args) throws Throwable {

        final Storage storage = storageLocator.getStorage(SERVICE_DATA_STORAGE);
        logger.log("DFLX0006", new Object[]{storage.getName()});
        transfer.importToComponent(storage, service);
        try {
            return invokeServiceMethod(service, methodName, args);
        } finally {
            logger.log("DFLX0007", new Object[]{storage.getName()});
            transfer.exportToStorage(service, storage);
        }
    }

    public StorageLocator getStorageLocator() {
        return storageLocator;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setStorageLocator(final StorageLocator storageLocator) {
        this.storageLocator = storageLocator;
    }

    public void setTransfer(final Transfer transfer) {
        this.transfer = transfer;
    }
}