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
package org.seasar.flex2.core.format.amf0.io.reader.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.io.AmfDataReader;
import org.seasar.flex2.core.format.amf0.io.reader.factory.Amf0DataReaderFactory;
import org.seasar.flex2.core.format.amf0.io.reader.factory.impl.Amf0DataReaderFactoryImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0ArrayReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0BooleanReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0DateReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0MixedArrayReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0NullReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0NumberReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0ObjectReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0ReferenceReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0StringReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0TypedObjectReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0UndefinedReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0UnsupportedReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0XmlReaderImpl;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.framework.container.S2Container;

public class AmfDataReaderFactoryTest extends S2TestCase {

    private static String PATH = "amf0.dicon";

    public void testCreateDataReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(Amf0DataReaderFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof Amf0DataReaderFactoryImpl);
    }

    public void testCreateDataReader() throws Exception {
        S2Container container = getContainer();
        Amf0DataReaderFactory factory = (Amf0DataReaderFactory) container
                .getComponent(Amf0DataReaderFactory.class);

        AmfDataReader reader;

        reader = factory.createDataReader(Amf0TypeDef.NUMBER);
        assertTrue("1", reader instanceof Amf0NumberReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.BOOLEAN);
        assertTrue("2", reader instanceof Amf0BooleanReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.STRING);
        assertTrue("3", reader instanceof Amf0StringReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.OBJECT);
        assertTrue("4", reader instanceof Amf0ObjectReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.NULL);
        assertTrue("5", reader instanceof Amf0NullReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.UNDEFINED);
        assertTrue("6", reader instanceof Amf0UndefinedReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.REFERENCE);
        assertTrue("7", reader instanceof Amf0ReferenceReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.MIXEDARRAY);
        assertTrue("8", reader instanceof Amf0MixedArrayReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.EOO);
        assertTrue("9", reader instanceof Amf0UnsupportedReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.ARRAY);
        assertTrue("A", reader instanceof Amf0ArrayReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.DATE);
        assertTrue("B", reader instanceof Amf0DateReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.LONGSTRING);
        assertTrue("C", reader instanceof Amf0UnsupportedReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.AS_OBJECT);
        assertTrue("D", reader instanceof Amf0UnsupportedReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.RECORDSET);
        assertTrue("E", reader instanceof Amf0UnsupportedReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.XMLSTRING);
        assertTrue("F", reader instanceof Amf0XmlReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.TYPEDOBJECT);
        assertTrue("10", reader instanceof Amf0TypedObjectReaderImpl);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}