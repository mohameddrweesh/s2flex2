/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf3.type.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.core.format.amf3.type.ExternalObjectInput;
import org.seasar.flex2.core.format.amf3.type.factory.ExternalObjectInputFactory;
import org.seasar.framework.container.S2Container;

public class ExternalObjectInputFactoryImpl implements
        ExternalObjectInputFactory {

    private S2Container container;

    public ExternalObjectInput createObjectInput(
            final DataInputStream inputStream) {
        final ExternalObjectInput input = (ExternalObjectInput) container
                .getComponent(ExternalObjectInput.class);
        input.initialize(inputStream);
        return input;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(final S2Container container) {
        this.container = container;
    }

}
