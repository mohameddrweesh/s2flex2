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
package org.seasar.flex2.message.format.amf.service.impl;

import org.seasar.flex2.message.format.amf.service.ServiceInvoker;
import org.seasar.flex2.message.format.amf.service.ServiceLocator;
import org.seasar.flex2.message.format.amf.service.exception.ServiceInvocationFailedRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class ServiceInvokerImpl implements ServiceInvoker {

    protected ServiceLocator serviceLocator;

    public ServiceInvokerImpl() {
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public Object invoke(String serviceName, String methodName, Object[] args)
            throws Throwable {

        final Object service = serviceLocator.getService(serviceName);
        return invokeServiceMethod(service, methodName, args);
    }

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public boolean supports(final String serviceName, final String methodName,
            final Object[] args) {
        return serviceLocator.isSupportService(serviceName, methodName);
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