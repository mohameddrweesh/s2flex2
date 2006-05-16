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

import org.seasar.flex2.rpc.amf.data.Amf3DataConstants;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.flex2.rpc.amf.util.Amf3DataUtil;
import org.seasar.framework.log.Logger;

public abstract class AbstractAmf3IntReaderImpl implements AmfDataReader {

    protected static final Logger logger = Logger
            .getLogger(AbstractAmf3IntReaderImpl.class);

    protected final int readInt(final DataInputStream inputStream)
            throws IOException {

        int integerData = inputStream.readUnsignedByte();

        if (integerData >= 0x00) {

            if ((integerData >>> 7) == 0x00) {
                return Amf3DataUtil.toInt(new int[] { integerData & 0x7F }, 1);
            }

            return readIntData(integerData, inputStream);
        }

        return 0;
    }

    private final int readIntData(final int intData,
            final DataInputStream inputStream) throws IOException {
        int[] list = new int[Amf3DataConstants.INTEGER_DATA_MAX_BYTES];
        int byte_count = 1;

        list[0] = intData & 0x7F;

        for (int i = 1; i < Amf3DataConstants.INTEGER_DATA_MAX_BYTES; i++) {
            list[i] = inputStream.readUnsignedByte();
            byte_count++;
            if ((list[i] >>> 7) == 0x00) {
                break;
            }
            if (byte_count < Amf3DataConstants.INTEGER_DATA_MAX_BYTES) {
                list[i] &= 0x7F;
            }
        }

        int result = 0;
        switch (intData >>> 6) {
            case 0x02:
                result = Amf3DataUtil.toInt(list, byte_count);
                break;

            case 0x03:
                result = Amf3DataUtil.toNegativeInt(list, byte_count);
                break;

            default:
        }

        return result;
    }
}