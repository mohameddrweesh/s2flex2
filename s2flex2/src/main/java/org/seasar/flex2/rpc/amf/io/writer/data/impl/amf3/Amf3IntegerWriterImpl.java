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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.flex2.rpc.amf.util.Amf3DataUtil;

public class Amf3IntegerWriterImpl extends AbstractAmf3IntWriterImpl {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        writeInteger((Integer) value, outputStream);
    }

    public void writeData(Object value, DataOutputStream outputStream)
            throws IOException {
        writeInteger((Integer) value, outputStream);
    }

    private final void writeInteger(Integer value, DataOutputStream outputStream)
            throws IOException {
        outputStream.writeByte(Amf3DataType.INTEGER);
        if (value.intValue() >= 0) {
            writeIntData(value.intValue(), outputStream);
        } else {
            writeNegativeIntData(value.intValue(), outputStream);
        }
    }

    private final void writeNegativeIntData(int value,
            DataOutputStream outputStream) throws IOException {
        int[] list = Amf3DataUtil.toNegativeIntBytes(value);
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