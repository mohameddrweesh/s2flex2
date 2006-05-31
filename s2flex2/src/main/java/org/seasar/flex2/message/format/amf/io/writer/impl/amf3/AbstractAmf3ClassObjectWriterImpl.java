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

import org.seasar.flex2.message.format.amf.Amf3Constants;

public abstract class AbstractAmf3ClassObjectWriterImpl extends
        AbstractAmf3TypedObjectWriterImpl {

    protected final void writeClassReference(final Object object,
            final DataOutputStream outputStream) throws IOException {

        int classIndex = getClassReferenceIndex(object.getClass());
        if (classIndex >= 0) {
            writeClassReferenceIndex(classIndex, outputStream);
        } else {
            writeClassReferenceDefine(object, outputStream);
        }
    }

    protected abstract void writeClassReferenceDefine(Object object,
            DataOutputStream outputStream) throws IOException;

    protected final void writeClassReferenceIndex(final int referenceIndex,
            final DataOutputStream outputStream) throws IOException {
        int referenceDef = Amf3Constants.OBJECT_INLINE;
        referenceDef = referenceIndex << 2 | referenceDef;
        writeIntData(referenceDef, outputStream);
    }

    protected final void writeTypeString(String propertyName,
            DataOutputStream outputStream) throws IOException {
        addStringReference(propertyName);
        writeUTF8String(propertyName, outputStream);
    }
}