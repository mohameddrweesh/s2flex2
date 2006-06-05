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
import org.seasar.flex2.message.format.amf.service.exception.ServiceNotFoundRuntimeException;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ClassUtil;

public class RemotingServiceLocatorOnHotDeployImpl extends
        RemotingServiceLocatorImpl {

    public Object getService(final String serviceName) {
        ComponentDef serviceComponentDef = getServiceComponentDefOnHotdeploy(serviceName);
        if (!canRegisterService(serviceComponentDef)) {
            throw new InvalidServiceRuntimeException(serviceName);
        }
        return serviceComponentDef.getComponent();
    }

    private final void copyMetadata(ComponentDef sourceComponentDef, ComponentDef distComponentDef) {
        for (int i = 0; i < sourceComponentDef.getMetaDefSize(); i++) {
            distComponentDef.addMetaDef(sourceComponentDef.getMetaDef(i));
        }
    }

    private final ComponentDef getServiceComponentDefOnHotdeploy(
            final String serviceName) {
        ComponentDef componentDef = getServiceComponentDef(serviceName);
        if (componentDef == null) {
            throw new ServiceNotFoundRuntimeException(serviceName);
        }
        reloadComponentDef(componentDef);

        ComponentDef reloadedComponentDef = getServiceComponentDef(serviceName);
        copyMetadata(componentDef, reloadedComponentDef);

        return reloadedComponentDef;
    }

    private final void reloadComponentDef(ComponentDef componentDef) {
        Class[] interfaces = componentDef.getComponentClass().getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            ClassUtil.forName(interfaces[i].getName());
        }
    }
}