/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf0.io.writer.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.framework.util.DomUtil;
import org.w3c.dom.Document;

public class Amf0XmlStringWriterImpl implements Amf0DataWriter {

    private static final void writeXmlData(final String xmlData,
            final DataOutputStream outputStream) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(xmlData.getBytes());
        outputStream.writeInt(baos.size());
        baos.writeTo(outputStream);
    }

    public boolean isWritableValue(final Object value) {
        return (value instanceof Document);
    }

    public void writeAmfData(final Object value, final DataOutputStream outputStream)
            throws IOException {
        write((Document) value, outputStream);
    }

    protected final void write(final Document document,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(Amf0TypeDef.XMLSTRING);
        writeXmlData(DomUtil.toString(document.getDocumentElement()),
                outputStream);
    }
}