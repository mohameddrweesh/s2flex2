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
package org.seasar.flex2.core.format.amf3.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.type.Amf3TypeDef;
import org.seasar.flex2.core.util.XmlUtil;
import org.w3c.dom.Document;

public class Amf3XmlStringWriterImpl extends AbstractAmf3ObjectWriterImpl {

    public int getDataTypeValue() {
        return Amf3TypeDef.XML_STRING;
    }

    public boolean isWritableValue(final Object value) {
        return (value instanceof Document);
    }

    protected void writeInlineObject(final Object object,
            final DataOutputStream outputStream) throws IOException {
        writeXml((Document) object, outputStream);
    }

    private final void writeXml(final Document document,
            final DataOutputStream outputStream) throws IOException {
        addObjectReference(document);
        writeUTF8String(XmlUtil.getXmlString(document), outputStream);
    }
}