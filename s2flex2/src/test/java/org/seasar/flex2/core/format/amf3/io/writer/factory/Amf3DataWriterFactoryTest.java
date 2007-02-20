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
package org.seasar.flex2.core.format.amf3.io.writer.factory;

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
import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0BooleanWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0NullWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0NumberWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0StringWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.factory.impl.Amf3DataWriterFactoryImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3ASObjectWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3ArrayWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3BigNumberWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3BooleanWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3CollectionWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3DateWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3IntegerWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3IteratorWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3NullWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3NumberWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3StringWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3TypedObjectWriterImpl;
import org.seasar.flex2.core.format.amf3.io.writer.impl.Amf3XmlStringWriterImpl;
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
        Amf3DataWriterFactory factory = (Amf3DataWriterFactory) container
                .getComponent(AmfDataWriterFactory.class);

        AmfDataWriter writer = factory.createDataWriter(null);

        assertTrue("1", writer instanceof Amf0NullWriterImpl);

        writer = factory.createDataWriter(new String());
        assertTrue("2", writer instanceof Amf0StringWriterImpl);

        writer = factory.createDataWriter(BigDecimal.valueOf(1));
        assertTrue("3", writer instanceof Amf3BigNumberWriterImpl);

        writer = factory.createDataWriter(new Double(0.0));
        assertTrue("4", writer instanceof Amf0NumberWriterImpl);

        writer = factory.createDataWriter(Boolean.FALSE);
        assertTrue("5", writer instanceof Amf0BooleanWriterImpl);

        writer = factory.createDataWriter(new Date());
        assertTrue("6", writer instanceof Amf3DateWriterImpl);

        writer = factory.createDataWriter(new String[1]);
        assertTrue("7", writer instanceof Amf3ArrayWriterImpl);

        writer = factory.createDataWriter((new ArrayList()).iterator());
        assertTrue("8", writer instanceof Amf3IteratorWriterImpl);

        writer = factory.createDataWriter(new ArrayList());
        assertTrue("9", writer instanceof Amf3CollectionWriterImpl);

        writer = factory.createDataWriter(new HashMap());
        assertTrue("10", writer instanceof Amf3ASObjectWriterImpl);

        writer = factory.createDataWriter(createXmlDocument());
        assertTrue("11", writer instanceof Amf3XmlStringWriterImpl);

        writer = factory.createDataWriter(new Object());
        assertTrue("12", writer instanceof Amf3TypedObjectWriterImpl);
    }

    public void testCreateDataValueWriter() throws Exception {
        S2Container container = getContainer();
        Amf3DataWriterFactory factory = (Amf3DataWriterFactory) container
                .getComponent(AmfDataWriterFactory.class);

        AmfDataWriter writer = factory.createAmf3DataWriter(null);

        assertTrue("1", writer instanceof Amf3NullWriterImpl);

        writer = factory.createAmf3DataWriter("");
        assertTrue("2", writer instanceof Amf3StringWriterImpl);

        writer = factory.createAmf3DataWriter(BigDecimal.valueOf(1));
        assertTrue("3", writer instanceof Amf3BigNumberWriterImpl);

        writer = factory.createAmf3DataWriter(new Integer(0));
        assertTrue("4", writer instanceof Amf3IntegerWriterImpl);

        writer = factory.createAmf3DataWriter(new Double(0.0));
        assertTrue("5", writer instanceof Amf3NumberWriterImpl);

        writer = factory.createAmf3DataWriter(Boolean.FALSE);
        assertTrue("6", writer instanceof Amf3BooleanWriterImpl);

        writer = factory.createAmf3DataWriter(new Date());
        assertTrue("7", writer instanceof Amf3DateWriterImpl);

        writer = factory.createAmf3DataWriter(new String[1]);
        assertTrue("8", writer instanceof Amf3ArrayWriterImpl);

        writer = factory.createAmf3DataWriter((new ArrayList()).iterator());
        assertTrue("9", writer instanceof Amf3IteratorWriterImpl);

        writer = factory.createAmf3DataWriter(new ArrayList());
        assertTrue("10", writer instanceof Amf3CollectionWriterImpl);

        writer = factory.createAmf3DataWriter(new HashMap());
        assertTrue("11", writer instanceof Amf3ASObjectWriterImpl);

        writer = factory.createAmf3DataWriter(createXmlDocument());
        assertTrue("12", writer instanceof Amf3XmlStringWriterImpl);

        writer = factory.createAmf3DataWriter(new Object());
        assertTrue("13", writer instanceof Amf3TypedObjectWriterImpl);
    }

    private Document createXmlDocument() throws FileNotFoundException {
        URL url = ResourceUtil.getResource("testXml.xml");
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