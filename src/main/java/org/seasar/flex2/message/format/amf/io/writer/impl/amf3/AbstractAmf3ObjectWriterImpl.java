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

import org.seasar.flex2.message.format.amf.io.Amf3References;
import org.seasar.flex2.message.format.amf.io.factory.Amf3ReferencesFactory;
import org.seasar.flex2.message.format.amf.type.Amf3TypeDef;

public abstract class AbstractAmf3ObjectWriterImpl extends
        AbstractAmf3UTF8StringWriterImpl {

    protected Amf3ReferencesFactory referencesFactory;

    public abstract int getDataTypeValue();

    public void setReferencesFactory(
            Amf3ReferencesFactory referencesFactory) {
        this.referencesFactory = referencesFactory;
    }

    public void write(final Object value,
            final DataOutputStream outputStream) throws IOException {
        writeAMF3DataMaker(outputStream);
        writeData(value, outputStream);
    }

    public void writeData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(getDataTypeValue());
        writeObject(value, outputStream);
    }

    protected final void addClassProperties(final Class clazz,
            final String[] properties) {
        getReferences().addClassProperties(clazz, properties);
    }

    protected final void addClassReference(final Class clazz) {
        getReferences().addClassReference(clazz);
    }

    protected final void addObjectReference(final Object object) {
        getReferences().addObjectReference(object);
    }

    protected final void addStringReference(final String object) {
        getReferences().addStringReference(object);
    }

    protected final int getClassReferenceIndex(final Class clazz) {
        return getReferences().getClassReferenceIndex(clazz);
    }

    protected final int getObjectReferenceIndex(final Object object) {
        return getReferences().getObjectReferenceIndex(object);
    }

    protected final int getStringReferenceIndex(final String object) {
        return getReferences().getStringReferenceIndex(object);
    }

    protected abstract void writeInlineObject(final Object object,
            final DataOutputStream outputStream) throws IOException;

    protected final void writeObject(final Object object,
            final DataOutputStream outputStream) throws IOException {
        int referenceIndex = getObjectReferenceIndex(object);
        if (referenceIndex >= 0) {
            writeReferenceIndex(referenceIndex, outputStream);
        } else {
            writeInlineObject(object, outputStream);
        }
    }

    protected final void writeReferenceIndex(final int index,
            final DataOutputStream outputStream) throws IOException {
        writeIntData(index << 1, outputStream);
    }

    private final Amf3References getReferences() {
        return referencesFactory.createReferences();
    }

    private final void writeAMF3DataMaker(final DataOutputStream outputStream)
            throws IOException {
        outputStream.writeByte(Amf3TypeDef.AMF3_DATA_MARKER);
    }
}