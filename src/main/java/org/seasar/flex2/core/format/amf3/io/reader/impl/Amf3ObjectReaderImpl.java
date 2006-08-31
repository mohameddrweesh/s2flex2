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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.Externalizable;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.io.reader.ASObjectReader;
import org.seasar.flex2.core.format.amf3.io.reader.ClassTypedObjectReader;
import org.seasar.flex2.core.format.amf3.io.reader.ExternalObjectReader;
import org.seasar.flex2.core.format.amf3.type.Amf3Object;
import org.seasar.framework.util.ClassUtil;

public class Amf3ObjectReaderImpl extends AbstractAmf3TypedObjectReaderImpl {

    private ASObjectReader asobjectReader;

    private ClassTypedObjectReader classTypedObjectReader;

    private ExternalObjectReader externalObjectReader;

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    public void setAsobjectReader(ASObjectReader asobjectReader) {
        this.asobjectReader = asobjectReader;
    }

    public void setClassTypedObjectReader(
            ClassTypedObjectReader classTypedObjectReader) {
        this.classTypedObjectReader = classTypedObjectReader;
    }

    public void setExternalObjectReader(
            ExternalObjectReader externalObjectReader) {
        this.externalObjectReader = externalObjectReader;
    }

    private Class readClass(final int objectDef,
            final DataInputStream inputStream) throws IOException {
        Class clazz = null;
        final String className = (String) stringReader.read(inputStream);

        switch (objectDef & Amf3Constants.OBJECT_ENCODING_TYPE) {
            case Amf3Constants.OBJECT_PROPERTY_LIST_ENCODED:
            case Amf3Constants.OBJECT_SINGLE_PROPERTY_ENCODED:
                clazz = ClassUtil.forName(className);
                break;

            case Amf3Constants.OBJECT_NAME_VALUE_ENCODED:
                if (className.length() == 0) {
                    clazz = Amf3Object.class;
                }
                break;

            default:
        }

        addClassReference(clazz);

        return clazz;
    }

    private final Class readClassDef(final int objectDef,
            final DataInputStream inputStream) throws IOException {

        Class clazz = null;

        switch (objectDef & Amf3Constants.CLASS_DEF_INLINE) {

            case Amf3Constants.CLASS_DEF_REFERENCE:
                clazz = getClassAt(objectDef >>> 2);
                break;

            case Amf3Constants.CLASS_DEF_INLINE:
                clazz = readClass(objectDef, inputStream);
                break;
        }

        return clazz;
    }

    private final Object readObjectData(final int objectDef,
            final DataInputStream inputStream) throws IOException {

        final Class clazz = readClassDef(objectDef, inputStream);

        Object object = null;
        do {

            if (clazz == null) {
                break;
            }

            if (clazz == Amf3Object.class) {
                object = asobjectReader.read(inputStream);
                break;
            }

            if (Externalizable.class.isAssignableFrom(clazz)) {
                object = externalObjectReader.read(clazz, inputStream);
                break;
            }

            object = classTypedObjectReader.read(objectDef, clazz, inputStream);
        } while (false);

        return object;
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readObjectData(reference, inputStream);
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }
}