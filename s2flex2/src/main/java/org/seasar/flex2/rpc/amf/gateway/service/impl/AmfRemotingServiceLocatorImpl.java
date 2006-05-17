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
package org.seasar.flex2.rpc.amf.gateway.service.impl;

import org.seasar.flex2.rpc.amf.gateway.service.AmfRemotingServiceLocator;
import org.seasar.flex2.rpc.amf.gateway.service.AmfRemotingServiceType;
import org.seasar.flex2.rpc.gateway.service.ServiceRepository;
import org.seasar.flex2.rpc.gateway.service.exception.InvalidServiceRuntimeException;
import org.seasar.flex2.rpc.gateway.service.impl.ServiceLocatorImpl;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class AmfRemotingServiceLocatorImpl extends ServiceLocatorImpl implements
        AmfRemotingServiceLocator {

    private ServiceRepository repository;

    public Object getService(final String serviceName) {
        Object service = null;

        if (repository.hasService(serviceName)) {
            service = repository.getService(serviceName);
        } else {
            service = super.getService(serviceName);
            if (canRegisterService(service)) {
                repository.addService(serviceName, service);
            } else {
                throw new InvalidServiceRuntimeException( serviceName );
            }
        }

        return service;
    }

    private final boolean canRegisterService(Object service) {

        S2Container root = container.getRoot();
        ComponentDef componentDef = root.getComponentDef(service.getClass());

        return componentDef
                .getMetaDef(AmfRemotingServiceType.AMF_REMOTING_SERVICE) != null;
    }

    public ServiceRepository getRepository() {
        return repository;
    }

    public void setRepository(ServiceRepository repository) {
        this.repository = repository;
    }
}