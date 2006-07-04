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

import java.io.DataOutputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf3.io.DataOutput;
import org.seasar.flex2.core.format.amf3.io.ExternalizeDataOutput;
import org.seasar.flex2.core.format.amf3.io.factory.DataOutputFactory;
import org.seasar.flex2.core.format.amf3.io.factory.impl.ExternalizeDataOutputFactoryImpl;
import org.seasar.framework.container.S2Container;

public class ExternalDataOutputFactoryTest extends S2TestCase {

    private static String PATH = "amf3.dicon";

    public void testCreateReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(DataOutputFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof ExternalizeDataOutputFactoryImpl);
    }

    public void testCreateDataOutput() throws Exception {
        S2Container container = getContainer();
        DataOutputFactory factory = (DataOutputFactory) container
                .getComponent(DataOutputFactory.class);
        DataOutput output = factory.createDataOutput(new DataOutputStream(
                System.out));
        assertNotNull("1", output);
        assertTrue("2", output instanceof ExternalizeDataOutput);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}