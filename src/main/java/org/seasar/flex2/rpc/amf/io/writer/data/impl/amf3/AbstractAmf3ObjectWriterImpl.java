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

import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.flex2.rpc.amf.io.factory.Amf3ReferencesFactory;

public abstract class AbstractAmf3ObjectWriterImpl extends
        AbstractAmf3UTF8StringWriterImpl {

    protected Amf3ReferencesFactory referencesFactory;

    public abstract int getObjectType();

    public final void setReferencesFactory(
            Amf3ReferencesFactory referencesFactory) {
        this.referencesFactory = referencesFactory;
    }

    public final void write(final Object value,
            final DataOutputStream outputStream) throws IOException {
        writeAMF3DataMaker(outputStream);
        writeObjectData(value, outputStream);
    }

    public final void writeData(Object value, DataOutputStream outputStream)
            throws IOException {
        writeObjectData(value, outputStream);
    }

    protected final void addClassProperties(Class clazz, String[] properties) {
        referencesFactory.createReferences().addClassProperties(clazz,
                properties);
    }

    protected final void addClassReference(Class clazz) {
        referencesFactory.createReferences().addClassReference(clazz);
    }

    protected final void addObjectReference(Object object) {
        referencesFactory.createReferences().addObjectReference(object);
    }

    protected final void addStringReference(String object) {
        referencesFactory.createReferences().addStringReference(object);
    }

    protected final int getClassReferenceIndex(Class clazz) {
        return referencesFactory.createReferences().getClassReferenceIndex(
                clazz);
    }

    protected final int getObjectReferenceIndex(Object object) {
        return referencesFactory.createReferences().getObjectReferenceIndex(
                object);
    }

    protected final int getStringReferenceIndex(String object) {
        return referencesFactory.createReferences().getStringReferenceIndex(
                object);
    }

    protected abstract void processWriteObjectData(final Object object,
            final DataOutputStream outputStream) throws IOException;

    protected final void writeObjectData(final Object object,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(getObjectType());
        int referenceIndex = getObjectReferenceIndex(object);
        if (referenceIndex >= 0) {
            writeReferenceIndex(referenceIndex, outputStream);
        } else {
            processWriteObjectData(object, outputStream);
        }
    }

    protected final void writeReferenceIndex(int index,
            DataOutputStream outputStream) throws IOException {
        writeIntData(index << 1, outputStream);
    }

    private final void writeAMF3DataMaker(DataOutputStream outputStream)
            throws IOException {
        outputStream.writeByte(Amf3DataType.AMF3_DATA_MARKER);
    }
}