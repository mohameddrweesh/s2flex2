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
package org.seasar.flex2.rpc.amf.io.writer.data.factory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.message.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.message.format.amf.io.writer.factory.Amf3DataWriterFactory;
import org.seasar.flex2.message.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.message.format.amf.io.writer.factory.impl.Amf3DataWriterFactoryImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfBigDecimalWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfBooleanWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfNullWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfNumberWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfStringWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3ArrayWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3BigDecimalWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3BooleanWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3CollectionWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3DateWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3InitializedObjectWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3IntegerWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3IteratorWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3NullWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3NumberWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3StringWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3TypedClassObjectWriterImpl;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf3.Amf3XmlWriterImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.w3c.dom.Document;

public class Amf3DataWriterFactoryTest extends S2TestCase {

    private static String PATH = "amf3.dicon";

    public void testCreateDataWriterFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(AmfDataWriterFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof Amf3DataWriterFactoryImpl);
    }

    public void testCreateDataWriter() throws Exception {
        S2Container container = getContainer();
        AmfDataWriterFactory factory = (AmfDataWriterFactory) container
                .getComponent(AmfDataWriterFactory.class);

        AmfDataWriter writer = factory.createDataWriter(null);

        assertTrue("1", writer instanceof AmfNullWriterImpl);

        writer = factory.createDataWriter(new String());
        assertTrue("2", writer instanceof AmfStringWriterImpl);

        writer = factory.createDataWriter(BigDecimal.valueOf(1));
        assertTrue("3", writer instanceof AmfBigDecimalWriterImpl);

        writer = factory.createDataWriter(new Double(0.0));
        assertTrue("4", writer instanceof AmfNumberWriterImpl);

        writer = factory.createDataWriter(Boolean.FALSE);
        assertTrue("5", writer instanceof AmfBooleanWriterImpl);

        writer = factory.createDataWriter(new Date());
        assertTrue("6", writer instanceof Amf3DateWriterImpl);

        writer = factory.createDataWriter(new String[1]);
        assertTrue("7", writer instanceof Amf3ArrayWriterImpl);

        writer = factory.createDataWriter((new ArrayList()).iterator());
        assertTrue("8", writer instanceof Amf3IteratorWriterImpl);

        writer = factory.createDataWriter(new ArrayList());
        assertTrue("9", writer instanceof Amf3CollectionWriterImpl);

        writer = factory.createDataWriter(new HashMap());
        assertTrue("10", writer instanceof Amf3InitializedObjectWriterImpl);

        writer = factory.createDataWriter(createXmlDocument());
        assertTrue("11", writer instanceof Amf3XmlWriterImpl);

        writer = factory.createDataWriter(new Object());
        assertTrue("12", writer instanceof Amf3TypedClassObjectWriterImpl);
    }

    public void testCreateDataValueWriter() throws Exception {
        S2Container container = getContainer();
        Amf3DataWriterFactory factory = (Amf3DataWriterFactory) container
                .getComponent(AmfDataWriterFactory.class);

        AmfDataWriter writer = factory.createDataValueWriter(null);

        assertTrue("1", writer instanceof Amf3NullWriterImpl);

        writer = factory.createDataValueWriter(new String());
        assertTrue("2", writer instanceof Amf3StringWriterImpl);

        writer = factory.createDataValueWriter(BigDecimal.valueOf(1));
        assertTrue("3", writer instanceof Amf3BigDecimalWriterImpl);

        writer = factory.createDataValueWriter(new Integer(0));
        assertTrue("4", writer instanceof Amf3IntegerWriterImpl);

        writer = factory.createDataValueWriter(new Double(0.0));
        assertTrue("5", writer instanceof Amf3NumberWriterImpl);

        writer = factory.createDataValueWriter(Boolean.FALSE);
        assertTrue("6", writer instanceof Amf3BooleanWriterImpl);

        writer = factory.createDataValueWriter(new Date());
        assertTrue("7", writer instanceof Amf3DateWriterImpl);

        writer = factory.createDataValueWriter(new String[1]);
        assertTrue("8", writer instanceof Amf3ArrayWriterImpl);

        writer = factory.createDataValueWriter((new ArrayList()).iterator());
        assertTrue("9", writer instanceof Amf3IteratorWriterImpl);

        writer = factory.createDataValueWriter(new ArrayList());
        assertTrue("10", writer instanceof Amf3CollectionWriterImpl);

        writer = factory.createDataValueWriter(new HashMap());
        assertTrue("11", writer instanceof Amf3InitializedObjectWriterImpl);

        writer = factory.createDataValueWriter(createXmlDocument());
        assertTrue("12", writer instanceof Amf3XmlWriterImpl);

        writer = factory.createDataValueWriter(new Object());
        assertTrue("13", writer instanceof Amf3TypedClassObjectWriterImpl);
    }

    private Document createXmlDocument() throws FileNotFoundException {
        URL url = ResourceUtil
                .getResource("org/seasar/flex2/rpc/amf/io/testXml.xml");
        File testXml = new File(url.getPath());
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        Document xml = DocumentBuilderUtil.parse(builder,
                new BufferedInputStream(new FileInputStream(testXml)));
        return xml;
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}