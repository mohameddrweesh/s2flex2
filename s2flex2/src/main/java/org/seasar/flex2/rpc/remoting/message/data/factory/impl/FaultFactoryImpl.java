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
package org.seasar.flex2.rpc.remoting.message.data.factory.impl;

import org.seasar.flex2.rpc.remoting.message.data.Fault;
import org.seasar.flex2.rpc.remoting.message.data.factory.FaultFactory;
import org.seasar.framework.container.S2Container;

public class FaultFactoryImpl implements FaultFactory {

    private static final String getStackTraceString(Throwable t) {
        final StackTraceElement[] elements = t.getStackTrace();
        final StringBuffer buf = new StringBuffer(t.toString());
        buf.append('\n');
        for (int i = 0; i < elements.length; ++i) {
            buf.append(elements[i].toString());
            buf.append('\n');
        }
        return buf.toString();
    }

    private S2Container container;

    public Fault createFault(String type, String details, String description) {
        final Fault fault = (Fault) container.getComponent(Fault.class);
        fault.setType(type);
        fault.setDetails(details);
        fault.setDescription(description);

        return fault;
    }

    public Fault createFault(Throwable throwable) {
        return createFault(throwable.getClass().getName(),
                getStackTraceString(throwable), throwable.getMessage());
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }
}
