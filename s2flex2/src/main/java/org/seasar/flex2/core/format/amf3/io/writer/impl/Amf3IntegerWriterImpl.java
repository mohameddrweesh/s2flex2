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

import org.seasar.flex2.core.format.amf3.Amf3IntegerConstants;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.type.Amf3TypeDef;

public class Amf3IntegerWriterImpl extends Amf3IntWriterImpl implements
        Amf3DataWriter {

    public boolean isWritableValue(final Object value) {
        boolean isWritableValue = false;
        if ((value instanceof Integer) || (value instanceof Byte)
                || (value instanceof Short)) {
            final int data = ((Number) value).intValue();
            if ((data <= Amf3IntegerConstants.INTEGER_MAX)
                    && (data >= Amf3IntegerConstants.INTEGER_MIN)) {
                isWritableValue = true;
            }
        }
        return isWritableValue;
    }

    public void writeAmfData(final Object value, final DataOutputStream outputStream)
            throws IOException {
        writeInteger((Integer) value, outputStream);
    }

    public void writeAmf3Data(final Object value,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(Amf3TypeDef.INTEGER);
        writeInteger((Integer) value, outputStream);
    }

    private final void writeInteger(final Integer value,
            final DataOutputStream outputStream) throws IOException {
        writeIntData(value.intValue(), outputStream);
    }
}