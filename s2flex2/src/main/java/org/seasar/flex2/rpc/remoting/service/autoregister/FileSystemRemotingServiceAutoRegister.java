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
import org.seasar.framework.container.autoregister.FileSystemComponentAutoRegister;

public class FileSystemRemotingServiceAutoRegister extends
        FileSystemComponentAutoRegister {

    private RemotingServiceRepository repository;

    private RemotingServiceLocator locator;

    protected void register(final String className) {
        super.register(className);
        if (locator.isSupportService(className)) {
            String componentName = getNamingConvention()
                    .fromClassNameToComponentName(className);
            repository.addService(componentName, getContainer()
                    .getComponentDef(componentName));
        }
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }

    public void setLocator(RemotingServiceLocator locator) {
        this.locator = locator;
    }
}