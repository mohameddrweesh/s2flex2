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

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;

public class Amf0ObjectWriterImpl extends AbstractAmfObjectWriterImpl {

    public boolean isWritableValue(final Object value) {
        return (value instanceof Map);
    }

    protected void writeObjectData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        getSharedObject().addSharedObject(value);
        writeObjectData((Map) value, outputStream);
    }

    private final void writeObjectData(final Map value,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(Amf0TypeDef.OBJECT);

        writeObjectProperties(value, outputStream);

        outputStream.writeShort(0);
        outputStream.writeByte(Amf0TypeDef.EOO);
    }

    private final void writeObjectProperties(final Map value,
            final DataOutputStream outputStream) throws IOException {
        for (final Iterator i = value.entrySet().iterator(); i.hasNext();) {
            final Map.Entry e = (Map.Entry) i.next();
            outputStream.writeUTF(String.valueOf(e.getKey()));
            writeData(e.getValue(), outputStream);
        }
    }
}