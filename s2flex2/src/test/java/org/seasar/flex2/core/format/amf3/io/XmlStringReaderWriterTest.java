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
package org.seasar.flex2.core.format.amf3.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.DomUtil;
import org.seasar.framework.util.ResourceUtil;
import org.w3c.dom.Document;

public class XmlStringReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testXmlString() throws Exception {
        Document value = createXmlDocument();

        assertEquals("1", getXmlString(value), getXmlString((Document)getWriteReadData(value)));
    }

    private final Document createXmlDocument() throws FileNotFoundException {
        final URL url = ResourceUtil.getResource("testXml.xml");
        final File testXml = new File(url.getPath());
        final DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        return DocumentBuilderUtil.parse(builder, new BufferedInputStream(
                new FileInputStream(testXml)));
    }
    
    private final String getXmlString(final Document document) {
        return DomUtil.toString(document.getDocumentElement());
    }
}