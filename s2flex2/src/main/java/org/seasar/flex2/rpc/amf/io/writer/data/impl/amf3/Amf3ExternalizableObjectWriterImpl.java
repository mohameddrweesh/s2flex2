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

import org.seasar.flex2.io.DataOutput;
import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.io.external.Externalizable;
import org.seasar.flex2.rpc.amf.io.external.factory.DataOutputFactory;
import org.seasar.framework.beans.BeanDesc;

public class Amf3ExternalizableObjectWriterImpl extends
        Amf3TypedClassObjectWriterImpl {

    private DataOutputFactory dataOutputFactory;

    public void setDataOutputFactory(DataOutputFactory dataOutputFactory) {
        this.dataOutputFactory = dataOutputFactory;
    }

    protected final void writeClassDefine(final BeanDesc beanDesc,
            final DataOutputStream outputStream) throws IOException {
        int classDef = Amf3Constants.OBJECT_INLINE;
        classDef |= Amf3Constants.CLASS_DEF_INLINE;
        classDef |= Amf3Constants.PROPERTY_DEF_SINGLE;
        writeIntData(classDef, outputStream);
    }

    protected final void writeClassObjectData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        if (value instanceof Externalizable) {
            Externalizable externalizable = (Externalizable) value;
            DataOutput output = dataOutputFactory
                    .createDataOutput(outputStream);
            externalizable.writeExternal(output);
        }
    }

    protected final void writeClassProperties(BeanDesc beanDesc,
            final DataOutputStream outputStream) throws IOException {
    }
}