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

public class Amf3IntWriterImpl {
    private static final int INTEGER_DATA_MASK = 0x7F;

    private static final int INTEGER_INCLUDE_NEXT_SIGN = 0x80;

    protected static final void writeIntData(final int value,
            final DataOutputStream outputStream) throws IOException {

        if (value < 0) {
            outputStream.writeByte(value >> 22 & INTEGER_DATA_MASK
                    | INTEGER_INCLUDE_NEXT_SIGN);
            outputStream.writeByte(value >> 15 & INTEGER_DATA_MASK
                    | INTEGER_INCLUDE_NEXT_SIGN);
            outputStream.writeByte(value >> 8 & INTEGER_DATA_MASK
                    | INTEGER_INCLUDE_NEXT_SIGN);
            outputStream.writeByte(value & 0xFF);
        } else {
            if (value < 0x80) {
                outputStream.writeByte(value);
            } else if (value < 0x4000) {
                outputStream.writeByte(value >> 7 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value & INTEGER_DATA_MASK);
            } else if (value < 0x200000) {
                outputStream.writeByte(value >> 14 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value >> 7 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value & INTEGER_DATA_MASK);
            } else {
                outputStream.writeByte(value >> 22 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value >> 15 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value >> 8 & INTEGER_DATA_MASK
                        | INTEGER_INCLUDE_NEXT_SIGN);
                outputStream.writeByte(value & 0xFF);
            }
        }
    }
}