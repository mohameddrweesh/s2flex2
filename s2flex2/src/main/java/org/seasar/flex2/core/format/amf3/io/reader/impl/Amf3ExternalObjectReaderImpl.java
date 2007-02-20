/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.seasar.flex2.core.format.amf3.io.ExternalObjectInput;
import org.seasar.flex2.core.format.amf3.io.factory.ExternalObjectInputFactory;
import org.seasar.flex2.core.format.amf3.io.reader.ExternalObjectReader;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;

public class Amf3ExternalObjectReaderImpl extends AbstractAmf3ObjectReaderImpl
        implements ExternalObjectReader {

    private ExternalObjectInputFactory externalizeDataInputFactory;

    public final Externalizable read(final Class clazz,
            final DataInputStream inputStream) throws IOException {
        final Externalizable externalObject = (Externalizable) ClassUtil
                .newInstance(clazz);
        addObjectReference(externalObject);

        final ExternalObjectInput input = externalizeDataInputFactory
                .createObjectInput(inputStream);
        try {
            externalObject.readExternal(input);
        } catch (final ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }

        return externalObject;
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return null;
    }

    public void setExternalizeDataInputFactory(
            final ExternalObjectInputFactory dataInputFactory) {
        externalizeDataInputFactory = dataInputFactory;
    }

    protected Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return null;
    }

    protected Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return null;
    }
}