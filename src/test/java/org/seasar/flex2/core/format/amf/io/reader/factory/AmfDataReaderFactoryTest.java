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
package org.seasar.flex2.core.format.amf.io.reader.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf.io.reader.factory.AmfDataReaderFactory;
import org.seasar.flex2.core.format.amf.io.reader.factory.impl.AmfDataReaderFactoryImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfASObjectReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfArrayReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfArraySharedObjectReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfBooleanReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfCustomClassReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfDateReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfFlushedSharedObjectReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfNullReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfNumberReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfObjectReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfStringReaderImpl;
import org.seasar.flex2.core.format.amf.io.reader.impl.AmfXmlReaderImpl;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;
import org.seasar.framework.container.S2Container;

public class AmfDataReaderFactoryTest extends S2TestCase {

    private static String PATH = "amf0.dicon";

    public void testCreateDataReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(AmfDataReaderFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof AmfDataReaderFactoryImpl);
    }

    public void testCreateDataReader() throws Exception {
        S2Container container = getContainer();
        AmfDataReaderFactory factory = (AmfDataReaderFactory) container
                .getComponent(AmfDataReaderFactory.class);

        AmfDataReader reader;

        reader = factory.createDataReader(AmfTypeDef.NUMBER);
        assertTrue("1", reader instanceof AmfNumberReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.BOOLEAN);
        assertTrue("2", reader instanceof AmfBooleanReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.STRING);
        assertTrue("3", reader instanceof AmfStringReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.OBJECT);
        assertTrue("4", reader instanceof AmfObjectReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.NULL);
        assertTrue("5", reader instanceof AmfNullReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.FLUSHED_SHARED_OBJECT);
        assertTrue("6", reader instanceof AmfFlushedSharedObjectReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.ARRAY_SHARED_OBJECT);
        assertTrue("7", reader instanceof AmfArraySharedObjectReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.ARRAY);
        assertTrue("8", reader instanceof AmfArrayReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.DATE);
        assertTrue("9", reader instanceof AmfDateReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.AS_OBJECT);
        assertTrue("10", reader instanceof AmfASObjectReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.XML);
        assertTrue("11", reader instanceof AmfXmlReaderImpl);

        reader = factory.createDataReader(AmfTypeDef.CUSTOM_CLASS);
        assertTrue("12", reader instanceof AmfCustomClassReaderImpl);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}