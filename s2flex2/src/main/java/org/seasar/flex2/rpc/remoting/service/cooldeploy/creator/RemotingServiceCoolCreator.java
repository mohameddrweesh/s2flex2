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
package org.seasar.flex2.rpc.remoting.service.cooldeploy.creator;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.autoregister.ComponentCustomizer;
import org.seasar.framework.container.cooldeploy.creator.SinglePackageCoolCreator;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class RemotingServiceCoolCreator extends SinglePackageCoolCreator {

    private RemotingServiceLocator locator;

    private RemotingServiceRepository repository;

    public RemotingServiceCoolCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setMiddlePackageName(namingConvention.getServicePackageName());
        setNameSuffix(namingConvention.getServiceSuffix());
        setInstanceDef(InstanceDefFactory.SESSION);
    }

    public ComponentCustomizer getServiceCustomizer() {
        return getCustomizer();
    }

    public boolean loadComponentDef(String rootPackageName, Class clazz) {
        boolean isLoadComponetDef = super.loadComponentDef(rootPackageName,
                clazz);
        if (isLoadComponetDef) {
            final String className = clazz.getName();
            if (locator.isSupportService(className)) {
                final String componentName = getNamingConvention()
                        .fromClassNameToComponentName(className);
                repository.addService(componentName, getContainer()
                        .getComponentDef(componentName));
            }
        }

        return isLoadComponetDef;
    }

    public void setLocator(RemotingServiceLocator locator) {
        this.locator = locator;
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }

    /*
    public void setServiceCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }
    */
}