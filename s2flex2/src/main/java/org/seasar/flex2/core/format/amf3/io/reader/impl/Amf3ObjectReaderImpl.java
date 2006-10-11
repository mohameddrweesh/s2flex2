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

    public void setAsobjectReader(final ASObjectReader asobjectReader) {
        this.asobjectReader = asobjectReader;
    }

    public void setClassTypedObjectReader(
            final ClassTypedObjectReader classTypedObjectReader) {
        this.classTypedObjectReader = classTypedObjectReader;
    }

    public void setExternalObjectReader(
            final ExternalObjectReader externalObjectReader) {
        this.externalObjectReader = externalObjectReader;
    }

    private final Object readInlineClassObjectData(final int objectDef,
            final DataInputStream inputStream) throws IOException {
        Object object = null;
        final String className = (String) stringReader.read(inputStream);
        switch (objectDef & Amf3Constants.OBJECT_ENCODING_TYPE) {
            case Amf3Constants.OBJECT_PROPERTY_LIST_ENCODED: {
                final Class clazz = ClassUtil.forName(className);
                addClassReference(clazz);
                object = classTypedObjectReader.read(objectDef, clazz,
                        inputStream);
                break;
            }

            case Amf3Constants.OBJECT_SINGLE_PROPERTY_ENCODED: {
                final Class clazz = ClassUtil.forName(className);
                if (Externalizable.class.isAssignableFrom(clazz)) {
                    addClassReference(clazz);
                    object = externalObjectReader.read(clazz, inputStream);
                    break;
                }
                throw new RuntimeException("Unsupport class type."
                        + "class is [" + clazz + "].");
            }

            case Amf3Constants.OBJECT_NAME_VALUE_ENCODED: {
                addClassReference(Amf3Object.class);
                object = asobjectReader.read(inputStream);
                break;
            }

            case Amf3Constants.OBJECT_SINGLE_PROPERTY_NAME_VALUE_ENCODED:
                throw new RuntimeException("Unsupport encoding."
                        + "objectDef is [" + objectDef + "].");

            default:
                throw new RuntimeException("unknown object encoding. "
                        + "objectDef is [" + objectDef + "].");
        }

        return object;
    }

    private final Object readObjectData(final int objectDef,
            final DataInputStream inputStream) throws IOException {

        Object object = null;

        switch (objectDef & Amf3Constants.CLASS_DEF_INLINE) {

            case Amf3Constants.CLASS_DEF_REFERENCE: {
                object = readReferenceClassObjectData(objectDef, inputStream,
                        getClassAt(objectDef >>> 2));
                break;
            }

            case Amf3Constants.CLASS_DEF_INLINE: {
                object = readInlineClassObjectData(objectDef, inputStream);
                break;
            }

            default:
        }

        return object;
    }

    private final Object readReferenceClassObjectData(final int objectDef,
            final DataInputStream inputStream, final Class clazz)
            throws IOException {
        Object object = null;
        do {
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