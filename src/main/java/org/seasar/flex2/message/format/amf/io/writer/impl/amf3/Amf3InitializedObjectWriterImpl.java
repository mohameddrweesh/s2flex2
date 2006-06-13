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
package org.seasar.flex2.message.format.amf.io.writer.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.message.format.amf.Amf3Constants;
import org.seasar.flex2.message.format.amf.type.Amf3TypeDef;

public class Amf3InitializedObjectWriterImpl extends
        AbstractAmf3ClassObjectWriterImpl {

    public int getDataTypeValue() {
        return Amf3TypeDef.OBJECT;
    }

    protected void processWriteObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeObjectData((Map) object, outputStream);
    }

    protected final void writeClassReferenceDefine(final Object object,
            final DataOutputStream outputStream) throws IOException {
        addClassReference(object.getClass());
        int classDef = Amf3Constants.OBJECT_INLINE;
        classDef |= Amf3Constants.CLASS_DEF_INLINE;
        classDef |= Amf3Constants.PROPERTY_DEF_WITH_VALUE;
        writeIntData(classDef, outputStream);
        outputStream.writeByte(Amf3Constants.EMPTY_STRING_DATA);
    }

    private final void writeObjectData(final Map value,
            final DataOutputStream outputStream) throws IOException {
        addObjectReference(value);
        writeClassReference(value, outputStream);

        for (Iterator i = value.entrySet().iterator(); i.hasNext();) {
            writeObjectEntry(outputStream, (Map.Entry) i.next());
        }

        outputStream.writeByte(Amf3Constants.EMPTY_STRING_DATA);
    }

    private final void writeObjectEntry(final DataOutputStream outputStream,
            final Map.Entry entry) throws IOException {
        String propertyName = (String) entry.getKey();
        writeTypeString(propertyName, outputStream);
        writeElementData(entry.getValue(), outputStream);
    }
}