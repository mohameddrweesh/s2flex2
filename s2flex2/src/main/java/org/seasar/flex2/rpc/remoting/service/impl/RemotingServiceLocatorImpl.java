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

import org.seasar.flex2.rpc.remoting.service.RemotingServiceConstants;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.flex2.rpc.remoting.service.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.rpc.remoting.service.annotation.handler.AnnotationHandler;
import org.seasar.flex2.rpc.remoting.service.exception.InvalidServiceRuntimeException;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.ClassUtil;

public class RemotingServiceLocatorImpl implements RemotingServiceLocator {

    private static final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();

    protected static final boolean canRegisterService(
            final ComponentDef componentDef) {
        boolean canRegisterService = true;
        if (!hasRemotingServiceMetadata(componentDef)) {
            if (!hasRemotingServiceAnnotation(componentDef)) {
                canRegisterService = false;
            }
        }
        return canRegisterService;
    }

    private static final boolean hasRemotingServiceAnnotation(
            final ComponentDef componentDef) {
        return annotationHandler.hasRemotingService(componentDef);
    }

    private static final boolean hasRemotingServiceMetadata(
            final ComponentDef componentDef) {

        return componentDef
                .getMetaDef(RemotingServiceConstants.REMOTING_SERVICE) != null;
    }

    protected S2Container container;

    protected RemotingServiceRepository repository;

    public S2Container getContainer() {
        return this.container;
    }

    public RemotingServiceRepository getRepository() {
        return repository;
    }

    public Object getService(final String serviceName) {
        final ComponentDef serviceComponentDef;

        if (HotdeployUtil.isHotdeploy()) {
            repository.removeService(serviceName);
        }
        if (repository.hasService(serviceName)) {
            serviceComponentDef = repository.getService(serviceName);
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

    public boolean isSupportService(final ComponentDef componentDef) {
        boolean isSupport = repository.hasService(componentDef
                .getComponentName());

        if (!isSupport) {
            isSupport = canRegisterService(componentDef);
        }

        return isSupport;
    }

    public boolean isSupportService(final String serviceName) {
        boolean isSupport = repository.hasService(serviceName);

        if (!isSupport) {
            if (hasServiceComponentByName(serviceName)) {
                isSupport = canRegisterService(getServiceComponentDef(serviceName));
            }
        }

        return isSupport;
    }

    public void setContainer(final S2Container container) {
        this.container = container.getRoot();
    }

    public void setRepository(final RemotingServiceRepository repository) {
        this.repository = repository;
    }

    protected final ComponentDef getServiceComponentDef(final String serviceName) {
        final S2Container root = container;
        final ComponentDef componentDef;
        if (root.hasComponentDef(serviceName)) {
            componentDef = root.getComponentDef(serviceName);
        } else {
            final Class clazz = ClassUtil.forName(serviceName);
            componentDef = root.getComponentDef(clazz);
        }
        return componentDef;
    }

    private final boolean hasServiceComponentByName(final String serviceName) {
        final S2Container root = container;
        boolean isSupport = root.hasComponentDef(serviceName);
        if (!isSupport) {
            try {
                final Class clazz = ClassUtil.forName(serviceName);
                isSupport = root.hasComponentDef(clazz);
            } catch (final Throwable ignore) {
            }
        }

        return isSupport;
    }
}