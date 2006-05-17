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
package org.seasar.flex2.rpc.gateway.service.impl;

import org.seasar.flex2.rpc.gateway.service.ServiceLocator;
import org.seasar.flex2.rpc.gateway.service.exception.ServiceNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.ClassUtil;

public class ServiceLocatorImpl implements ServiceLocator {

    protected S2Container container;

    public ServiceLocatorImpl() {
    }

    public S2Container getContainer() {
        return this.container;
    }

    public Object getService( final String serviceName) {
        S2Container root = container.getRoot();
        Object component = null;
        Class clazz = null;
        if (root.hasComponentDef(serviceName)) {
            component = root.getComponent(serviceName);
        } else {
            clazz = ClassUtil.forName(serviceName);
            component = root.getComponent(clazz);
        }

        if (component == null) {
            throw new ServiceNotFoundRuntimeException(serviceName);
        }

        return component;
    }

    public boolean isSupportService( final String serviceName, final String methodName) {
        S2Container root = container.getRoot();
        if (root.hasComponentDef(serviceName)) {
            return true;
        }
        try {
            Class clazz = ClassUtil.forName(serviceName);
            if (root.hasComponentDef(clazz)) {
                return true;
            }
        } catch (Throwable ignore) {
        }
        return false;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }
}