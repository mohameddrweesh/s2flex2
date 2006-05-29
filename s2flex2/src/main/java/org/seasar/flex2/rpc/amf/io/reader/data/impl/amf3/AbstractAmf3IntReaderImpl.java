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
package org.seasar.flex2.rpc.amf.io.reader.data.impl.amf3;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.flex2.rpc.amf.io.util.Amf3DataUtil;

public abstract class AbstractAmf3IntReaderImpl implements AmfDataReader {

    protected final int readInt(final DataInputStream inputStream)
            throws IOException {

        int integerData = inputStream.readUnsignedByte();

        if (integerData >= 0x00) {

            if ((integerData >>> 7) == 0x00) {
                integerData = Amf3DataUtil.toInt(new int[] { integerData & 0x7F }, 1);
            } else {

                integerData = readIntData(integerData, inputStream);
            }
        }

        return integerData;
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
            return Amf3DataUtil.toInt(intBytes, intByteLength);
        } else {
            switch (intData >>> 6) {
                
                case 0x02:
                    result = Amf3DataUtil.toInt(intBytes, intByteLength);
                    break;
                    
                case 0x03:
                    result = Amf3DataUtil.toNegativeInt(intBytes,
                            intByteLength);
                    break;
                    
                default:
            }
        }

        return result;
    }
}