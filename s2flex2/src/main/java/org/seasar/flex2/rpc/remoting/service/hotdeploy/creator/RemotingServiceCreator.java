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
package org.seasar.flex2.rpc.remoting.service.hotdeploy.creator;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.hotdeploy.OndemandS2Container;
import org.seasar.framework.container.hotdeploy.creator.MultiPackageCreator;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.StringUtil;

public class RemotingServiceCreator extends MultiPackageCreator {

    public RemotingServiceCreator(NamingConvention namingConvention) {
        super(namingConvention);
        addMiddlePackageName("service");
        setNameSuffix("Service");
        setInstanceDef(InstanceDefFactory.REQUEST);
    }

    public boolean loadComponentDef(OndemandS2Container container,
            String subsystemPackageName, Class clazz) {
        boolean loadedComponentDef = super.loadComponentDef(container,
                subsystemPackageName, clazz);
        if (loadedComponentDef && isTarget(subsystemPackageName, clazz)) {

            ComponentDef componentDef = container
                    .getComponentDef(RemotingServiceRepository.class);

            RemotingServiceRepository repository = (RemotingServiceRepository) componentDef
                    .getComponent();

            removeService(repository, clazz);
        }

        return loadedComponentDef;
    }

    private final void removeService(RemotingServiceRepository repository,
            Class serviceClass) {
        if (serviceClass.isInterface()) {
            String serviceInterfaceName = serviceClass.getName();
            int lashDotPosition = serviceInterfaceName.lastIndexOf('.');
            String serviceName = StringUtil.decapitalize(serviceInterfaceName
                    .substring(lashDotPosition + 1));
            repository.removeService(serviceName);
        }
    }
}
