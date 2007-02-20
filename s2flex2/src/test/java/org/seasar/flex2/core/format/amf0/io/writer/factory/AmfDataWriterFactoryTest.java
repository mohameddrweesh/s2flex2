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
package org.seasar.flex2.core.format.amf0.io.writer.factory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf0.io.writer.factory.impl.Amf0DataWriterFactoryImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0ArrayWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0BooleanWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0DateWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0NullWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0NumberWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0ObjectWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0StringWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0TypedObjectWriterImpl;
import org.seasar.flex2.core.format.amf0.io.writer.impl.Amf0XmlStringWriterImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.w3c.dom.Document;

public class AmfDataWriterFactoryTest extends S2TestCase {

    private static String PATH = "amf0.dicon";

    public void testCreateDataWriterFactory() throws Exception {
        S2Container container = getContainer();
        Object factory = container.getComponent(Amf0DataWriterFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof Amf0DataWriterFactoryImpl);
    }

    public void testCreateDataWriter() throws Exception {
        S2Container container = getContainer();
        Amf0DataWriterFactory factory = (Amf0DataWriterFactory) container
                .getComponent(Amf0DataWriterFactory.class);

        AmfDataWriter writer = factory.createDataWriter(null);

        assertTrue("1", writer instanceof Amf0NullWriterImpl);

        writer = factory.createDataWriter("");
        assertTrue("2", writer instanceof Amf0StringWriterImpl);

        // writer = factory.createDataWriter(BigDecimal.valueOf(1));
        // assertTrue("3", writer instanceof Amf0BigDecimalWriterImpl);

        writer = factory.createDataWriter(new Double(0.0));
        assertTrue("4", writer instanceof Amf0NumberWriterImpl);

        writer = factory.createDataWriter(Boolean.FALSE);
        assertTrue("5", writer instanceof Amf0BooleanWriterImpl);

        writer = factory.createDataWriter(new Date());
        assertTrue("6", writer instanceof Amf0DateWriterImpl);

        writer = factory.createDataWriter(new String[1]);
        assertTrue("7", writer instanceof Amf0ArrayWriterImpl);

        // writer = factory.createDataWriter((new ArrayList()).iterator());
        // assertTrue("8", writer instanceof Amf0IteratorWriterImpl);

        // writer = factory.createDataWriter(new ArrayList());
        // assertTrue("9", writer instanceof Amf0CollectionWriterImpl);

        writer = factory.createDataWriter(new HashMap());
        assertTrue("10", writer instanceof Amf0ObjectWriterImpl);

        writer = factory.createDataWriter(createXmlDocument());
        assertTrue("11", writer instanceof Amf0XmlStringWriterImpl);

        writer = factory.createDataWriter(new Object());
        assertTrue("12", writer instanceof Amf0TypedObjectWriterImpl);
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