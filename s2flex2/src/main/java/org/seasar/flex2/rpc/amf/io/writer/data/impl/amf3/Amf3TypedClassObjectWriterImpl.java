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

import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class Amf3TypedClassObjectWriterImpl extends AbstractAmf3ClassObjectWriterImpl {

    public int getObjectType() {
        return Amf3DataType.OBJECT;
    }

    protected void processWriteObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeClassObject(object, outputStream);
    }

    protected final void writeClassReferenceDef(final Object object,
            final DataOutputStream outputStream) throws IOException {
        addClassReference(object.getClass());

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        writeClassDef(beanDesc, outputStream);
        writeClassName(object, outputStream);

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            writeClassPropertyName(outputStream, (PropertyDesc) beanDesc
                    .getPropertyDesc(i));
        }
    }

    private final void writeClassDef(final BeanDesc beanDesc,
            final DataOutputStream outputStream) throws IOException {
        int int_data = Amf3Constants.OBJECT_INLINE;
        int_data |= Amf3Constants.CLASS_DEF_INLINE;
        int_data = (beanDesc.getPropertyDescSize() << 4) | int_data;
        writeIntData(int_data, outputStream);
    }

    private final void writeClassName(final Object object,
            final DataOutputStream outputStream) throws IOException {
        String type = object.getClass().getName();
        writeTypeString(type, outputStream);
    }

    private final void writeClassObject(final Object value,
            final DataOutputStream outputStream) throws IOException {
        addObjectReference(value);
        writeClassReference(value, outputStream);

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            writeClassPropertyValue(outputStream, (PropertyDesc) beanDesc
                    .getPropertyDesc(i), value);
        }
    }

    private final void writeClassPropertyName(
            final DataOutputStream outputStream, final PropertyDesc propertyDef)
            throws IOException {
        if (propertyDef.hasReadMethod()) {
            writeTypeString(propertyDef.getPropertyName(), outputStream);
        }
    }

    private final void writeClassPropertyValue(
            final DataOutputStream outputStream,
            final PropertyDesc propertyDef, final Object target)
            throws IOException {
        if (propertyDef.hasReadMethod()) {
            writeElementData(propertyDef.getValue(target), outputStream);
        }
    }
}