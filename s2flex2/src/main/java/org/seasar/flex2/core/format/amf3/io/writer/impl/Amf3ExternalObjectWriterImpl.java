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
package org.seasar.flex2.core.format.amf3.io.writer.impl;

import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.type.ExternalObjectOutput;
import org.seasar.flex2.core.format.amf3.type.factory.ExternalObjectOutputFactory;
import org.seasar.framework.beans.BeanDesc;

public class Amf3ExternalObjectWriterImpl extends Amf3TypedObjectWriterImpl {

    private ExternalObjectOutputFactory externalizeDataOutputFactory;

    public boolean isWritableValue(final Object value) {
        return (value instanceof Externalizable);
    }

    public void setExternalizeDataOutputFactory(
            final ExternalObjectOutputFactory dataOutputFactory) {
        externalizeDataOutputFactory = dataOutputFactory;
    }

    protected final void writeClassDefine(final BeanDesc beanDesc,
            final DataOutputStream outputStream) throws IOException {
        int classDef = Amf3Constants.OBJECT_INLINE;
        classDef |= Amf3Constants.CLASS_DEF_INLINE;
        classDef |= Amf3Constants.OBJECT_SINGLE_PROPERTY_ENCODED;
        writeIntData(classDef, outputStream);
    }

    protected final void writeClassObjectProperties(final Object value,
            final DataOutputStream outputStream) throws IOException {
        final Externalizable externalObject = (Externalizable) value;
        final ExternalObjectOutput externalizeDataOutput = externalizeDataOutputFactory
                .createObjectOutput(outputStream);
        externalObject.writeExternal(externalizeDataOutput);
    }

    protected final void writeClassProperties(final BeanDesc beanDesc,
            final DataOutputStream outputStream) throws IOException {
    }
}