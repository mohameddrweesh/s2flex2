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
package org.seasar.flex2.core.format.amf3.io.factory;

import java.io.DataInputStream;
import java.io.ObjectInput;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf3.io.ExternalObjectInput;
import org.seasar.flex2.core.format.amf3.io.factory.impl.ExternalObjectInputFactoryImpl;
import org.seasar.framework.container.S2Container;

public class ExternalDataInputFactoryTest extends S2TestCase {

    private static String PATH = "amf3.dicon";

    public void testCreateReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container
                .getComponent(ExternalObjectInputFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof ExternalObjectInputFactoryImpl);
    }

    public void testCreateObejctInput() throws Exception {
        S2Container container = getContainer();
        ExternalObjectInputFactory factory = (ExternalObjectInputFactory) container
                .getComponent(ExternalObjectInputFactory.class);
        ObjectInput input = factory.createObjectInput(new DataInputStream(
                System.in));
        assertNotNull("1", input);
        assertTrue("2", input instanceof ExternalObjectInput);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}