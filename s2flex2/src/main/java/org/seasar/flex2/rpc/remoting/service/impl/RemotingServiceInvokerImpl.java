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

import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.exception.ServiceInvocationFailedRuntimeException;
import org.seasar.flex2.util.data.storage.Storage;
import org.seasar.flex2.util.data.storage.StorageLocator;
import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class RemotingServiceInvokerImpl implements RemotingServiceInvoker {

    private final static String SERVICE_DATA_STORAGE = "serviceDataStorage";

    private StorageLocator storageLocator;

    private Transfer transfer;

    protected RemotingServiceLocator remotingServiceLocator;

    public RemotingServiceLocator getServiceLocator() {
        return remotingServiceLocator;
    }

    public StorageLocator getStorageLocator() {
        return storageLocator;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public Object invoke(String serviceName, String methodName, Object[] args)
            throws Throwable {

        final Object service = remotingServiceLocator.getService(serviceName);
        final Storage storage = storageLocator.getStorage(SERVICE_DATA_STORAGE);
        transfer.importToComponent(storage, service);
        try {
            return invokeServiceMethod(service, methodName, args);

        } finally {
            transfer.exportToStorage(service, storage);
        }
    }

    public void setServiceLocator(RemotingServiceLocator serviceLocator) {
        this.remotingServiceLocator = serviceLocator;
    }

    public void setStorageLocator(StorageLocator storageLocator) {
        this.storageLocator = storageLocator;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public boolean supports(final String serviceName, final String methodName,
            final Object[] args) {
        return remotingServiceLocator.isSupportService(serviceName, methodName);
    }

    protected final Object invokeServiceMethod(final Object service,
            final String methodName, final Object[] args) throws Throwable {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(service.getClass());
        try {
            return beanDesc.invoke(service, methodName, args);
        } catch (Throwable e) {
            throw new ServiceInvocationFailedRuntimeException(service
                    .getClass().getName(), methodName, e.getCause());
        }
    }
}