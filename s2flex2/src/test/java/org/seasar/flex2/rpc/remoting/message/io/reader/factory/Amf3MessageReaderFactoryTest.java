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
package org.seasar.flex2.rpc.remoting.message.io.reader.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.reader.factory.AmfDataReaderFactory;
import org.seasar.flex2.core.format.amf0.io.reader.factory.impl.Amf0DataReaderFactoryImpl;
import org.seasar.flex2.rpc.remoting.message.io.reader.factory.impl.MessageReaderFactoryImpl;
import org.seasar.flex2.rpc.remoting.message.io.reader.impl.Amf3MessageReaderImpl;
import org.seasar.flex2.rpc.remoting.message.io.reader.impl.AmfMessageReaderImpl;
import org.seasar.framework.container.S2Container;

public class Amf3MessageReaderFactoryTest extends S2TestCase {

    private static String PATH = "remoting_amf3.dicon";

    public void testCreateReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(MessageReaderFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof MessageReaderFactoryImpl);
    }

    public void testCreateReader() throws Exception {
        S2Container container = getContainer();
        Object object = container.getComponent(Amf3MessageReaderImpl.class);

        assertTrue("1", object instanceof Amf3MessageReaderImpl);

        AmfMessageReaderImpl reader = (AmfMessageReaderImpl) object;
        AmfDataReaderFactory dataFactory = reader.getDataReaderFactory();
        assertNotNull("2", dataFactory);
        assertTrue("3", dataFactory instanceof Amf0DataReaderFactoryImpl);

    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}