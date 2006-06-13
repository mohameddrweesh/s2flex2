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

import org.seasar.flex2.message.format.amf.Amf3Constants;
import org.seasar.flex2.message.format.amf.io.writer.Amf3DataWriter;

public abstract class AbstractAmf3IntWriterImpl implements Amf3DataWriter {

    protected final void writeIntData(final int value,
            final DataOutputStream outputStream) throws IOException {
        int[] list = getVariableIntBytes(value);
        for (int i = list.length - 1; i >= 1; i--) {
            outputStream.writeByte(list[i]
                    | Amf3Constants.INTEGER_INCLUDE_NEXT_BYTE);
        }
        outputStream.writeByte(list[0]);
    }

    protected final int[] getVariableIntBytes(final int value) {
        int intByteLength = getIntByteLength(value);
        if (intByteLength < 0) {
            return new int[0];
        }

        int[] list = new int[intByteLength];
        int intValue = value;

        if (intByteLength < 4) {
            list[0] = intValue & 0x7F;
            intValue = intValue >>> 7;
        } else {
            list[0] = intValue & 0xFF;
            intValue = intValue >>> 8;
        }

        for (int i = 1; i < intByteLength; i++) {
            list[i] = intValue & 0x7F;
            intValue = intValue >>> 7;
        }
        return list;
    }

    private final int getIntByteLength(final int value) {
        if (value < 0) {
            return 4;
        }
        if (value >= 0x10000000) {
            return -1;
        }
        if (value >= 0x200000) {
            return 4;
        }
        if (value >= 0x4000) {
            return 3;
        }
        if (value >= 0x80) {
            return 2;
        }
        if (value >= 0x0) {
            return 1;
        }
        return -1;
    }
}