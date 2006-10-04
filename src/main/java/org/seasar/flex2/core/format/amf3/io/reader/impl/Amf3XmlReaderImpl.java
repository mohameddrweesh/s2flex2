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

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.util.XmlUtil;
import org.w3c.dom.Document;

public class Amf3XmlReaderImpl extends AbstractAmf3UTF8StringReaderImpl {

    private static final String DEFAULT_TAG_PREFIX = "<root>";

    private static final String DEFAULT_TAG_SUFFIX = "</root>";

    private static final String readXmlStringData(final int xmlDef,
            final DataInputStream inputStream) throws IOException {
        String xmlStringData = readStringData(xmlDef, inputStream);
        if ((xmlStringData.indexOf('<') < 0)
                || (xmlStringData.indexOf('<') > 1)) {
            xmlStringData = DEFAULT_TAG_PREFIX + xmlStringData
                    + DEFAULT_TAG_SUFFIX;
        }
        return xmlStringData;
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Document readXmlData(final int xmlDef,
            final DataInputStream inputStream) throws IOException {
        final Document xml = XmlUtil.getXmlDocument(readXmlStringData(xmlDef,
                inputStream));
        addObjectReference(xml);

        return xml;
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readXmlData(reference, inputStream);
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }
}