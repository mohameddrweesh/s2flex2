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
package org.seasar.flex2.core.format.amf0.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

public class Amf0ArrayReaderImpl extends AbstractAmf0ObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readArray(inputStream);
    }

    private final Object readArrayElements(final DataInputStream inputStream)
            throws IOException {
        final byte dataType = inputStream.readByte();
        return readData(dataType, inputStream);
    }

    protected final Object[] readArray(final DataInputStream inputStream)
            throws IOException {
        final int length = inputStream.readInt();
        final Object array[] = new Object[length];
        addSharedObject(array);

        for (int i = 0; i < length; i++) {
            array[i] = readArrayElements(inputStream);
        }
        return array;
    }
}