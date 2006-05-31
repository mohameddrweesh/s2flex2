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
package org.seasar.flex2.message.format.amf.io.reader.impl.amf3;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.message.format.amf.Amf3Constants;
import org.seasar.flex2.message.format.amf.io.reader.AmfDataReader;

public abstract class AbstractAmf3IntReaderImpl implements AmfDataReader {

    protected final int readInt(final DataInputStream inputStream)
            throws IOException {

        int integerData = inputStream.readUnsignedByte();

        if (integerData >= 0x00) {

            if ((integerData >>> 7) == 0x00) {
                integerData = toInt(new int[] { integerData & 0x7F }, 1);
            } else {

                integerData = readIntData(integerData, inputStream);
            }
        }

        return integerData;
    }

    private final int getNegativeInt( final int[] list, final int bytes) {
        return toInt(list, bytes) | 0xF0000000;
    }
    
    private final int readIntData(final int intData,
            final DataInputStream inputStream) throws IOException {
        int[] intBytes = new int[Amf3Constants.INTEGER_DATA_MAX_BYTES];
        int intByteLength = 1;

        intBytes[0] = intData & 0x7F;

        for (int i = 1; i < Amf3Constants.INTEGER_DATA_MAX_BYTES; i++) {
            intBytes[i] = inputStream.readUnsignedByte();
            intByteLength++;
            if ((intBytes[i] >>> 7) == 0x00) {
                break;
            }
            if (intByteLength < Amf3Constants.INTEGER_DATA_MAX_BYTES) {
                intBytes[i] &= 0x7F;
            }
        }

        int result = 0;        
        if (intByteLength < Amf3Constants.INTEGER_DATA_MAX_BYTES) {
            return toInt(intBytes, intByteLength);
        } else {
            switch (intData >>> 6) {
                
                case 0x02:
                    result = toInt(intBytes, intByteLength);
                    break;
                    
                case 0x03:
                    result = getNegativeInt(intBytes,
                            intByteLength);
                    break;
                    
                default:
            }
        }

        return result;
    }

    private final int toInt( final int[] list, final int bytes) {
        int intValue = list[bytes - 1];
        int offset = 0;

        if (bytes < Amf3Constants.INTEGER_DATA_MAX_BYTES) {
            offset = 7;
        } else {
            offset = 8;
        }

        for (int i = bytes - 1; i > 0; i--) {
            intValue |= (list[i - 1] << offset);
            offset += 7;
        }

        return intValue;
    }
}