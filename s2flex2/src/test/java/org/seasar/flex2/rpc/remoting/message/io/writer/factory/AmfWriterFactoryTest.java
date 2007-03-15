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
package org.seasar.flex2.rpc.remoting.message.io.writer.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.core.format.amf0.io.writer.factory.Amf0DataWriterFactory;
import org.seasar.flex2.rpc.remoting.message.io.writer.factory.impl.MessageWriterFactoryImpl;
import org.seasar.flex2.rpc.remoting.message.io.writer.impl.AmfMessageWriterImpl;
import org.seasar.framework.container.S2Container;

public class AmfWriterFactoryTest extends S2TestCase {

    private static String PATH = "remoting_amf0.dicon";

    public void testCreateWriterFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(MessageWriterFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof MessageWriterFactoryImpl);
    }

    public void testCreateWriter() throws Exception {
        S2Container container = getContainer();
        Object object = container.getComponent(AmfMessageWriterImpl.class);

        assertTrue("1", object instanceof AmfMessageWriterImpl);

        AmfMessageWriterImpl writer = (AmfMessageWriterImpl) object;
        AmfDataWriterFactory dataFactory = writer.getAmfDataWriterFactory();
        assertNotNull("2", dataFactory);
        assertTrue("3", dataFactory instanceof Amf0DataWriterFactory);

    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}