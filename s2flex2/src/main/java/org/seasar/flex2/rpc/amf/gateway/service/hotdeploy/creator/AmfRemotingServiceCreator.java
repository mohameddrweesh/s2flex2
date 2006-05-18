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
package org.seasar.flex2.rpc.amf.gateway.service.hotdeploy.creator;

import org.seasar.flex2.rpc.amf.gateway.service.AmfRemotingServiceRepository;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.hotdeploy.creator.InterfaceCentricSinglePackageCreator;

public class AmfRemotingServiceCreator extends
        InterfaceCentricSinglePackageCreator {

    public AmfRemotingServiceCreator() {
        setMiddlePackageName("service");
        setNameSuffix("Service");
        setInstanceDef(InstanceDefFactory.REQUEST);
    }

    public boolean loadComponentDef(S2Container container, Class clazz) {
        boolean loadedComponentDef = super.loadComponentDef(container, clazz);
        if (loadedComponentDef) {
            S2Container root = container.getRoot();
            AmfRemotingServiceRepository repository = (AmfRemotingServiceRepository) root
                    .getComponent(AmfRemotingServiceRepository.class);
            repository.removeService(clazz);
        }

        return loadedComponentDef;
    }

}
