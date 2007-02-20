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
package org.seasar.flex2.core.format.amf3.io.reader.factory;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.AmfDataReader;
import org.seasar.flex2.core.format.amf0.io.reader.factory.Amf0DataReaderFactory;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0ArrayReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0BooleanReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0NullReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0NumberReaderImpl;
import org.seasar.flex2.core.format.amf0.io.reader.impl.Amf0StringReaderImpl;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.flex2.core.format.amf3.io.reader.factory.impl.Amf3DataReaderFactoryImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3ArrayReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3BooleanFalseReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3BooleanTrueReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3ByteArrayReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3DataReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3DateReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3IntegerReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3NullReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3NumberReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3ObjectReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3StringReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3XmlReaderImpl;
import org.seasar.flex2.core.format.amf3.io.reader.impl.Amf3XmlStringReaderImpl;
import org.seasar.flex2.core.format.amf3.type.Amf3TypeDef;
import org.seasar.framework.container.S2Container;

public class Amf3DataReaderFactoryTest extends S2TestCase {

    private static String PATH = "amf3.dicon";

    public void testCreateDataReaderFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(Amf0DataReaderFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof Amf3DataReaderFactoryImpl);
    }

    public void testCreateDataReader() throws Exception {
        S2Container container = getContainer();
        Amf0DataReaderFactory factory = (Amf0DataReaderFactory) container
                .getComponent(Amf3DataReaderFactory.class);

        AmfDataReader reader;

        reader = factory.createDataReader(Amf0TypeDef.NUMBER);
        assertTrue("1", reader instanceof Amf0NumberReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.BOOLEAN);
        assertTrue("2", reader instanceof Amf0BooleanReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.STRING);
        assertTrue("3", reader instanceof Amf0StringReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.NULL);
        assertTrue("5", reader instanceof Amf0NullReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.ARRAY);
        assertTrue("6", reader instanceof Amf0ArrayReaderImpl);

        reader = factory.createDataReader(Amf0TypeDef.AMF3_DATA_MARKER);
        assertTrue("11", reader instanceof Amf3DataReaderImpl);

    }

    public void testCreateAmf3DataReader() throws Exception {
        S2Container container = getContainer();
        Amf3DataReaderFactory factory = (Amf3DataReaderFactory) container
                .getComponent(Amf0DataReaderFactory.class);

        AmfDataReader reader;


        reader = factory.createAmf3DataReader(Amf3TypeDef.NULL);
        assertTrue("1", reader instanceof Amf3NullReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.BOOLEAN_FALSE);
        assertTrue("2", reader instanceof Amf3BooleanFalseReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.BOOLEAN_TRUE);
        assertTrue("3", reader instanceof Amf3BooleanTrueReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.INTEGER);
        assertTrue("4", reader instanceof Amf3IntegerReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.NUMBER);
        assertTrue("5", reader instanceof Amf3NumberReaderImpl);
        
        reader = factory.createAmf3DataReader(Amf3TypeDef.STRING);
        assertTrue("6", reader instanceof Amf3StringReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.XML);
        assertTrue("7", reader instanceof Amf3XmlReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.DATE);
        assertTrue("8", reader instanceof Amf3DateReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.ARRAY);
        assertTrue("9", reader instanceof Amf3ArrayReaderImpl);
        
        reader = factory.createAmf3DataReader(Amf3TypeDef.OBJECT);
        assertTrue("A", reader instanceof Amf3ObjectReaderImpl);

        reader = factory.createAmf3DataReader(Amf3TypeDef.XML_STRING);
        assertTrue("B", reader instanceof Amf3XmlStringReaderImpl);
        
        reader = factory.createAmf3DataReader(Amf3TypeDef.BYTEARRAY);
        assertTrue("C", reader instanceof Amf3ByteArrayReaderImpl);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}