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
package org.seasar.flex2.core.format.amf3.io.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.core.format.amf3.io.ExternalObjectOutput;
import org.seasar.flex2.core.format.amf3.io.factory.ExternalObjectOutputFactory;
import org.seasar.flex2.core.format.amf3.io.impl.ExternalObjectOutputImpl;
import org.seasar.framework.container.S2Container;

public class ExternalObjectOutputFactoryImpl implements
        ExternalObjectOutputFactory {

    private S2Container container;

    public ExternalObjectOutput createObjectOutput(DataOutputStream outputStream) {
        final ExternalObjectOutputImpl output = (ExternalObjectOutputImpl) container
                .getComponent(ExternalObjectOutput.class);
        output.setOutputStream(outputStream);
        return output;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }
}