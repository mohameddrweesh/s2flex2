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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.Amf3IntegerConstants;

public class Amf3IntReaderImpl {

    protected static final int readInt(final DataInputStream inputStream)
            throws IOException {

        final int firstByte = inputStream.readUnsignedByte();

        int result;
        if (firstByte < 0x80) {
            result = firstByte;
        } else {
            result = readIntData(firstByte, inputStream);
        }

        return result;
    }

    private static final int readIntData(final int firstByte,
            final DataInputStream inputStream) throws IOException {

        final int[] intBytes = new int[Amf3IntegerConstants.INTEGER_MAX_DATA_BYTES];

        final int intByteLength = readIntDataBytes(firstByte, inputStream,
                intBytes);

        int intData = toInt(intBytes, intByteLength);

        if ((intByteLength >= Amf3IntegerConstants.INTEGER_MAX_DATA_BYTES)
                && (firstByte >= Amf3IntegerConstants.INTEGER_NAGATIVE_SIGN)) {
            intData |= 0xF0000000;
        }

        return intData;
    }

    private static final int readIntDataBytes(final int firstByte,
            final DataInputStream inputStream, final int[] intBytes)
            throws IOException {
        int intByteLength = 1;

        intBytes[0] = firstByte & Amf3IntegerConstants.INTEGER_DATA_MASK;

        for (int i = 1; i < Amf3IntegerConstants.INTEGER_MAX_DATA_BYTES; i++) {
            intBytes[i] = inputStream.readUnsignedByte();
            intByteLength++;
            if ((intBytes[i] >>> 7) == 0x00) {
                break;
            }
        }
        return intByteLength;
    }

    private static final int toInt(final int[] list, final int bytes) {
        int intValue = list[bytes - 1];

        int offset;
        if (bytes < Amf3IntegerConstants.INTEGER_MAX_DATA_BYTES) {
            offset = 7;
        } else {
            offset = 8;
        }

        for (int i = bytes - 1; i > 0; i--) {
            intValue |= (list[i - 1] & Amf3IntegerConstants.INTEGER_DATA_MASK) << offset;
            offset += 7;
        }

        return intValue;
    }
}