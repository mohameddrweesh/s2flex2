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
package org.seasar.flex2.core.format.amf3.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.type.Amf3TypeDef;

public class Amf3IntegerWriterImpl extends AbstractAmf3IntWriterImpl {

    public void write(final Object value, final DataOutputStream outputStream)
            throws IOException {
        writeInteger((Integer) value, outputStream);
    }

    public void writeData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(Amf3TypeDef.INTEGER);
        writeInteger((Integer) value, outputStream);
    }

    private final int[] getNegativeIntBytes(final int value) {
        return getVariableIntBytes(value);
    }

    private final void writeInteger(final Integer value,
            final DataOutputStream outputStream) throws IOException {
        if (value.intValue() >= 0) {
            writeIntData(value.intValue(), outputStream);
        } else {
            writeNegativeIntData(value.intValue(), outputStream);
        }
    }

    private final void writeNegativeIntData(final int value,
            final DataOutputStream outputStream) throws IOException {
        final int[] list = getNegativeIntBytes(value);
        if (list.length == 4) {
            outputStream.writeByte(Amf3Constants.INTEGER_INCLUDE_NEXT_BYTE
                    | Amf3Constants.INTEGER_NEGATIVE_SING | list[3]);

            for (int i = list.length - 2; i >= 1; i--) {
                outputStream.writeByte(Amf3Constants.INTEGER_INCLUDE_NEXT_BYTE
                        | list[i]);
            }
            outputStream.writeByte(list[0]);
        }
    }
}