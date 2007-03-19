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

import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;

public class Amf0StringWriterImpl implements Amf0DataWriter {

    public boolean isWritableValue(final Object value) {
        return (value instanceof String);
    }

    public void writeAmfData(final Object value, final DataOutputStream outputStream)
            throws IOException {
        write((String) value, outputStream);
    }

    protected void write(final String value, final DataOutputStream outputStream)
            throws IOException {
        outputStream.writeByte(Amf0TypeDef.STRING);
        outputStream.writeUTF(value);
    }
}