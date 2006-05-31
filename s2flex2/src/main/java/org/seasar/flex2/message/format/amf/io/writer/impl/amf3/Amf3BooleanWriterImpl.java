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
package org.seasar.flex2.message.format.amf.io.writer.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.message.format.amf.io.writer.Amf3DataWriter;
import org.seasar.flex2.message.format.amf.type.Amf3DataType;

public class Amf3BooleanWriterImpl implements Amf3DataWriter {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        writeBoolean((Boolean) value, outputStream);
    }

    public void writeData(Object value, DataOutputStream outputStream)
            throws IOException {
        writeBoolean((Boolean) value, outputStream);
    }

    private final void writeBoolean(final Boolean value,
            final DataOutputStream outputStream) throws IOException {
        if (value.booleanValue()) {
            outputStream.writeByte(Amf3DataType.BOOLEAN_TRUE);
        } else {
            outputStream.writeByte(Amf3DataType.BOOLEAN_FALSE);
        }
    }
}