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
package org.seasar.flex2.core.format.amf0.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class Amf0TypedObjectWriterImpl extends AbstractAmfObjectWriterImpl
        implements Amf0DataWriter {

    public boolean isWritableValue(final Object value) {
        return (value instanceof Object);
    }

    protected void writeObjectData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        getSharedObject().addSharedObject(value);
        writeCustomClassData(value, outputStream);
    }

    private final void writeCustomClassData(final Object value,
            final DataOutputStream outputStream) throws IOException {

        final String type = value.getClass().getName();
        outputStream.writeByte(Amf0TypeDef.TYPEDOBJECT);
        outputStream.writeUTF(type);

        writeCustomClassProperties(value, outputStream);

        outputStream.writeShort(0);
        outputStream.writeByte(Amf0TypeDef.EOO);
    }

    private final void writeCustomClassProperties(final Object value,
            final DataOutputStream outputStream) throws IOException {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.isReadable()) {
                outputStream.writeUTF(pd.getPropertyName());
                writeData(pd.getValue(value), outputStream);
            }
        }
    }
}