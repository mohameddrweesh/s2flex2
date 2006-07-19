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

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.unit.S2FrameworkTestCase;

public class FileSystemRemotingServiceAutoRegisterTest extends
        S2FrameworkTestCase {

    private S2Container child;
    
    private NamingConvention namingConvention;

    private RemotingServiceRepository repository;
    
    public RemotingServiceRepository getRepository() {
        return repository;
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }

    public void setUpServiceRegisterNoMetadata() throws Exception {
        include("autoRegister.dicon");
    }

    public void testServiceRegisterNoMetadata() throws Exception {
        TestService1 testService = (TestService1) child
                .getComponent(TestService1.class);
        
        assertNotNull("1", testService);
        
        boolean hasComponent = repository.hasService(namingConvention.fromClassNameToComponentName(TestService1.class.getName()));
        
        assertTrue("2", hasComponent);
    }
}
