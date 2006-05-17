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

import org.seasar.flex2.rpc.amf.data.Amf3DataConstants;
import org.seasar.flex2.rpc.amf.type.Amf3DataType;

public class Amf3ArrayWriterImpl extends AbstractAmf3TypedObjectWriterImpl {

    public final int getObjectType() {
        return Amf3DataType.ARRAY;
    }

    protected void processWriteObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeArrayElements((Object[]) object, outputStream);
    }

    private final void writeArrayElement(final DataOutputStream outputStream,
            Object element) throws IOException {
        writeElementData(element, outputStream);
    }

    private final void writeArrayElements(final Object[] array,
            final DataOutputStream outputStream) throws IOException {
        addObjectReference(array);
        
        int arrayDef = array.length << 1 | Amf3DataConstants.OBJECT_INLINE;
        writeIntData(arrayDef, outputStream);
        outputStream.writeByte(Amf3DataConstants.EMPTY_STRING_DATA);
        
        for (int i = 0; i < array.length; i++) {
            writeArrayElement(outputStream, array[i]);
        }
    }
}