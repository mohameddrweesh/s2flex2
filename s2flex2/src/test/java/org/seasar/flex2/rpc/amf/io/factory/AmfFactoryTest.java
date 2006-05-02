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
package org.seasar.flex2.rpc.amf.io.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.reader.factory.impl.AmfReaderFactoryImpl;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.writer.factory.impl.AmfWriterFactoryImpl;
import org.seasar.framework.container.S2Container;

public class AmfFactoryTest extends S2TestCase {

    private static String PATH = "AmfFactoryTest.dicon";

    public void testCreateReader() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(AmfReaderFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof AmfReaderFactoryImpl);
    }

    public void testCreateWriter() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(AmfWriterFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof AmfWriterFactoryImpl);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}