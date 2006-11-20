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
package org.seasar.flex2.rpc.remoting.service.customizer;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRegister;
import org.seasar.flex2.rpc.remoting.service.impl.RemotingServiceRegisterImpl;
import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.ComponentDef;

public class RemotingServiceCustomizer extends RemotingServiceRegisterImpl
        implements ComponentCustomizer {
    
    private RemotingServiceRegister remotingServiceRegister;

    public void setRemotingServiceRegister(
            RemotingServiceRegister remotingServiceRegister) {
        this.remotingServiceRegister = remotingServiceRegister;
    }

    public void customize(final ComponentDef componentDef) {
        remotingServiceRegister.register(componentDef);
    }
}