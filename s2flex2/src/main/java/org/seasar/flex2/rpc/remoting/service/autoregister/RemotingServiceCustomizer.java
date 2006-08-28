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
package org.seasar.flex2.rpc.remoting.service.autoregister;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.autoregister.ComponentCustomizer;

public class RemotingServiceCustomizer implements ComponentCustomizer {

    private S2Container container;

    private RemotingServiceLocator locator;

    private RemotingServiceRepository repository;

    public void customize( final ComponentDef componentDef) {
        if (locator == null && repository == null) {
            setupRemotingServiceComponents();
        }
        
        if (locator.isSupportService(componentDef)) {
            repository.addService(componentDef.getComponentName(),
                    componentDef);
        }
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    private final void setupRemotingServiceComponents() {
        final S2Container container = this.container.getRoot();
        locator = (RemotingServiceLocator) container
                .getComponent(RemotingServiceLocator.class);
        repository = (RemotingServiceRepository) container
                .getComponent(RemotingServiceRepository.class);
    }
}
