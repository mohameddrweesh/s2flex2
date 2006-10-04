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
package org.seasar.flex2.core.format.amf.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmfArrayReaderImpl extends AbstractAmfObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readArray(inputStream);
    }

    private final void readArrayElements(final DataInputStream inputStream,
            final ArrayList array) throws IOException {
        final byte dataType = inputStream.readByte();
        array.add(readData(dataType, inputStream));
    }

    protected final List readArray(final DataInputStream inputStream)
            throws IOException {
        final ArrayList array = new ArrayList();
        addSharedObject(array);
        final int length = inputStream.readInt();
        for (int i = 0; i < length; i++) {
            readArrayElements(inputStream, array);
        }
        return array;
    }
}