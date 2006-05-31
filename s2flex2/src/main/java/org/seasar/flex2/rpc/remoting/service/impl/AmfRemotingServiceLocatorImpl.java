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

import org.seasar.flex2.rpc.gateway.service.exception.InvalidServiceRuntimeException;
import org.seasar.flex2.rpc.gateway.service.impl.ServiceLocatorImpl;
import org.seasar.flex2.rpc.remoting.service.AmfRemotingServiceConstants;
import org.seasar.flex2.rpc.remoting.service.AmfRemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.AmfRemotingServiceRepository;
import org.seasar.flex2.rpc.remoting.service.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.rpc.remoting.service.annotation.handler.AnnotationHandler;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class AmfRemotingServiceLocatorImpl extends ServiceLocatorImpl implements
        AmfRemotingServiceLocator {

    private final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();

    private AmfRemotingServiceRepository repository;

    public void cleanServiceOf(Class serviceClass) {
        repository.removeService(serviceClass);
    }

    public AmfRemotingServiceRepository getRepository() {
        return repository;
    }

    public Object getService(final String serviceName) {
        Object service = null;

        if (repository.hasService(serviceName)) {
            service = repository.getService(serviceName);
        } else {
            service = super.getService(serviceName);
            if (canRegisterService(service.getClass())) {
                repository.addService(serviceName, service);
            } else {
                throw new InvalidServiceRuntimeException(serviceName);
            }
        }

        return service;
    }

    public void setRepository(AmfRemotingServiceRepository repository) {
        this.repository = repository;
    }

    private final boolean canRegisterService(final Class clazz) {

        S2Container root = container.getRoot();
        ComponentDef componentDef = root.getComponentDef(clazz);
        if (!hasAmfRemotingServiceMetadata(componentDef)) {
            if (!hasAmfRemotingServiceAnnotation(componentDef)) {
                return false;
            }
        }
        return true;
    }

    private final boolean hasAmfRemotingServiceAnnotation(
            final ComponentDef componentDef) {
        return annotationHandler.hasAmfRemotingService(componentDef);
    }

    private final boolean hasAmfRemotingServiceMetadata(
            final ComponentDef componentDef) {

        return componentDef
                .getMetaDef(AmfRemotingServiceConstants.AMF_REMOTING_SERVICE) != null;
    }
}