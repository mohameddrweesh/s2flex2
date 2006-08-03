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

import java.util.Map;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.exception.ServiceInvocationFailedRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;

public abstract class AbstractRemotingServiceInvokerImpl implements
        RemotingServiceInvoker {

    private static final Logger logger = Logger.getLogger(RemotingServiceInvoker.class);
    
    private Map forbiddenMethodMap;
    
    protected RemotingServiceLocator remotingServiceLocator;

    public RemotingServiceLocator getServiceLocator() {
        return remotingServiceLocator;
    }

    public abstract Object invoke(String serviceName, String methodName,
            Object[] args) throws Throwable;

    public void setForbiddenMethodMap(Map forbiddenMethodMap) {
        this.forbiddenMethodMap = forbiddenMethodMap;
    }

    public void setServiceLocator(RemotingServiceLocator serviceLocator) {
        this.remotingServiceLocator = serviceLocator;
    }
    
    public boolean supports(final String serviceName, final String methodName,
            final Object[] args) {
        boolean isSupport = false;
        if( !isForbiddenMethod( methodName )){
            isSupport = remotingServiceLocator.isSupportService(serviceName);
        }
        return isSupport;
    }

    private final boolean isForbiddenMethod( final String methodName) {
        return forbiddenMethodMap.containsKey(methodName);
    }

    protected final Object invokeServiceMethod(final Object service,
            final String methodName, final Object[] args) throws Throwable {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(service.getClass());
        try {
            return beanDesc.invoke(service, methodName, args);
        } catch (Throwable e) {
            logger.debug("invokeServiceMethod", e);
            throw new ServiceInvocationFailedRuntimeException(service
                    .getClass().getName(), methodName, e.getCause());
        }
    }
}