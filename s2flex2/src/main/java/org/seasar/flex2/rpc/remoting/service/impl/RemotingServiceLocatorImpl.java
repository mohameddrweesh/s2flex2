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

import org.seasar.flex2.message.format.amf.service.exception.InvalidServiceRuntimeException;
import org.seasar.flex2.message.format.amf.service.impl.ServiceLocatorImpl;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceConstants;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.flex2.rpc.remoting.service.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.rpc.remoting.service.annotation.handler.AnnotationHandler;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.ClassUtil;

public class RemotingServiceLocatorImpl extends ServiceLocatorImpl implements
        RemotingServiceLocator {

    protected RemotingServiceRepository repository;

    private final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();

    public void cleanServiceOf(Class serviceClass) {
        repository.removeService(serviceClass);
    }

    public RemotingServiceRepository getRepository() {
        return repository;
    }

    public Object getService(final String serviceName) {
        ComponentDef serviceComponentDef = null;

        if (repository.hasService(serviceName)) {
            serviceComponentDef = (ComponentDef)repository.getService(serviceName);
        } else {
            serviceComponentDef = getServiceComponentDef(serviceName);
            if (canRegisterService(serviceComponentDef)) {
                repository.addService(serviceName, serviceComponentDef);
            } else {
                throw new InvalidServiceRuntimeException(serviceName);
            }
        }

        return serviceComponentDef.getComponent();
    }

    public boolean isSupportService(final String serviceName,
            final String methodName) {
        boolean isSupport = repository.hasService(serviceName);

        if (!isSupport) {
            if (super.isSupportService(serviceName, methodName)) {
                ComponentDef componentDef = getServiceComponentDef(serviceName);
                isSupport = canRegisterService( componentDef );
            }
        }

        return isSupport;
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }

    protected final boolean canRegisterService(final ComponentDef componentDef) {
        if (!hasAmfRemotingServiceMetadata(componentDef)) {
            if (!hasAmfRemotingServiceAnnotation(componentDef)) {
                return false;
            }
        }
        return true;
    }

    protected ComponentDef getServiceComponentDef(String serviceName) {
        S2Container root = container.getRoot();
        ComponentDef componentDef;
        if (root.hasComponentDef(serviceName)) {
            componentDef = root.getComponentDef(serviceName);
        } else {
            Class clazz = ClassUtil.forName(serviceName);
            componentDef = root.getComponentDef(clazz);
        }
        return componentDef;
    }

    private final boolean hasAmfRemotingServiceAnnotation(
            final ComponentDef componentDef) {
        return annotationHandler.hasAmfRemotingService(componentDef);
    }

    private final boolean hasAmfRemotingServiceMetadata(
            final ComponentDef componentDef) {

        return componentDef
                .getMetaDef(RemotingServiceConstants.REMOTING_SERVICE) != null;
    }
}