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

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ArrayMap;

public class RemotingServiceRepositoryImpl implements RemotingServiceRepository {
    protected final Map serviceCache;

    public RemotingServiceRepositoryImpl() {
        serviceCache = Collections.synchronizedMap(new ArrayMap(32));
    }

    public void addService(final String serviceName, final ComponentDef service) {
        if (!serviceCache.containsKey(serviceName)) {
            serviceCache.put(serviceName, service);
        }
    }

    public void clearService() {
        serviceCache.clear();
    }

    public ComponentDef getService(final String serviceName) {
        return (ComponentDef) serviceCache.get(serviceName);
    }

    public Set getServiceNames() {
        return serviceCache.keySet();
    }

    public boolean hasService(final String serviceName) {
        return serviceCache.containsKey(serviceName);
    }

    public void removeService(final String serviceName) {
        serviceCache.remove(serviceName);
    }
}