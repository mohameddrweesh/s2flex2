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
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.type.Amf3References;
import org.seasar.flex2.core.format.amf3.type.factory.Amf3ReferencesFactory;

public abstract class AbstractAmf3ObjectReaderImpl extends
        AbstractAmf3IntReaderImpl {

    protected Amf3ReferencesFactory referencesFactory;

    public void setReferencesFactory(Amf3ReferencesFactory referencesFactory) {
        this.referencesFactory = referencesFactory;
    }

    private final Amf3References getReferences() {
        return referencesFactory.createReferences();
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

    protected final Class getClassAt(final int index) {
        return getReferences().getClassAt(index);
    }

    protected final int getClassReferenceIndex(final Class clazz) {
        return getReferences().getClassReferenceIndex(clazz);
    }

    protected final Object getObjectAt(final int index) {
        return getReferences().getObjectAt(index);
    }

    protected final int getObjectReferenceIndex(final Object object) {
        return getReferences().getObjectReferenceIndex(object);
    }

    protected final String[] getPropertiesOf(final Class clazz) {
        return getReferences().getPropertiesAt(clazz);
    }

    protected final String getStringAt(final int index) {
        return getReferences().getStringAt(index);
    }

    protected final int getStringReferenceIndex(final String object) {
        return getReferences().getStringReferenceIndex(object);
    }

    protected abstract Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException;

    protected final Object readObject(final DataInputStream inputStream)
            throws IOException {
        Object result = null;

        final int reference = readInt(inputStream);
        switch (reference & Amf3Constants.OBJECT_INLINE) {

            case Amf3Constants.OBJECT_REFERENCE:
                result = readReferencedObject(reference, inputStream);
                break;

            case Amf3Constants.OBJECT_INLINE:
                result = readInlinedObject(reference, inputStream);
                break;

            default:
        }
        return result;
    }

    protected abstract Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException;
}