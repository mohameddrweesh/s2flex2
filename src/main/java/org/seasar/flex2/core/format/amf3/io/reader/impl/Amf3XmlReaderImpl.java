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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.flex2.core.format.amf3.io.CharsetType;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.w3c.dom.Document;

public class Amf3XmlReaderImpl extends AbstractAmf3UTF8StringReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readXmlData(reference, inputStream);
    }

    protected final Object readReferencedObject(int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }

    private final Document readXmlData(int xmlDef,
            final DataInputStream inputStream) throws IOException {
        String xmlStringData = readStringData(xmlDef, inputStream);
        Document xml = getXmlDocument(xmlStringData);
        addObjectReference(xml);

        return xml;
    }
    
    private final Document getXmlDocument(String xml) {
        ByteArrayInputStream bain;
        try {
            bain = new ByteArrayInputStream(xml.getBytes(CharsetType.UTF8));
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(bain);
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        return DocumentBuilderUtil.parse(builder, bis);
    }
}