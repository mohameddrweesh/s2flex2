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
package org.seasar.flex2.message.io.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.message.io.DataInput;
import org.seasar.flex2.message.io.ExternalizeDataInput;
import org.seasar.flex2.message.io.factory.DataInputFactory;
import org.seasar.framework.container.S2Container;

public class ExternalizeDataInputFactoryImpl implements DataInputFactory {

    private S2Container container;
    
    public DataInput createDataIpput(DataInputStream inputStream) {
        ExternalizeDataInput input = (ExternalizeDataInput)container.getComponent(ExternalizeDataInput.class);
        input.setInputStream(inputStream);
        return input;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

}
