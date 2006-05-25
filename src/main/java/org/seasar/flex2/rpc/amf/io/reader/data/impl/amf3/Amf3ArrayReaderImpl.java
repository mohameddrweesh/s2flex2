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
import java.util.ArrayList;
import java.util.List;

public class Amf3ArrayReaderImpl extends AbstractAmf3TypedObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        List array = readArrayData(reference, inputStream);
        return array;
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) {
        return getObjectAt(reference >>> 1);
    }

    private final List readArrayData(final int arrayDef,
            final DataInputStream inputStream) throws IOException {
        int arrayLength = arrayDef >> 1;
        List array = new ArrayList(arrayLength);
        addObjectReference(array);

        inputStream.readByte(); // class define byte
        for (int i = 0; i < arrayLength; i++) {
            byte dataType = inputStream.readByte();
            Object item = writeElementData(dataType, inputStream);
            array.add(item);
        }
        return array;
    }
}