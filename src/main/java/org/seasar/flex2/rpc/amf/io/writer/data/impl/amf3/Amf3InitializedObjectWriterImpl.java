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
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.rpc.amf.data.Amf3DataConstants;
import org.seasar.flex2.rpc.amf.type.Amf3DataType;

public class Amf3InitializedObjectWriterImpl extends AbstractAmf3ClassObjectWriterImpl {

    public int getObjectType() {
        return Amf3DataType.OBJECT;
    }

    protected void processWriteObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeObjectData((Map) object, outputStream);
    }

    protected final void writeClassReferenceDef(final Object object,
            final DataOutputStream outputStream) throws IOException {
        addClassReference(object.getClass());
        int int_data = Amf3DataConstants.OBJECT_INLINE;
        int_data |= Amf3DataConstants.CLASS_DEF_INLINE;
        int_data |= Amf3DataConstants.UNTYPED_OBJECT;
        writeIntData(int_data, outputStream);
        outputStream.writeByte(Amf3DataConstants.EMPTY_STRING_DATA); // there is no
        // class-def
    }

    private final void writeObjectData(final Map value,
            final DataOutputStream outputStream) throws IOException {
        addObjectReference(value);
        writeClassReference(value, outputStream);

        for (Iterator i = value.entrySet().iterator(); i.hasNext();) {
            writeObjectEntry(outputStream, (Map.Entry) i.next());
        }

        outputStream.writeByte(Amf3DataConstants.EMPTY_STRING_DATA);
    }

    private final void writeObjectEntry(final DataOutputStream outputStream,
            final Map.Entry entry) throws IOException {
        String propertyName = (String) entry.getKey();
        writeTypeString(propertyName, outputStream);
        writeElementData(entry.getValue(), outputStream);
    }
}