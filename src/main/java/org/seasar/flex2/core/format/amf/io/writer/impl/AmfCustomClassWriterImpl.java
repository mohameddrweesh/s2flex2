/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class AmfCustomClassWriterImpl extends AbstractAmfObjectWriterImpl
        implements AmfDataWriter {

    private final void writeCustomClassData(final Object value,
            final DataOutputStream outputStream) throws IOException {

        final String type = value.getClass().getName();
        outputStream.writeByte(AmfTypeDef.CUSTOM_CLASS);
        outputStream.writeUTF(type);

        writeCustomClassProperties(value, outputStream);

        outputStream.writeShort(0);
        outputStream.writeByte(AmfTypeDef.EOM);
    }

    private final void writeCustomClassProperties(final Object value,
            final DataOutputStream outputStream) throws IOException {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(i);
            // if (pd.isReadable()) {//public fieldに対応するときには、こちら。s2 2.4.17以降が条件。
            if (pd.hasReadMethod()) {
                outputStream.writeUTF(pd.getPropertyName());
                writeData(pd.getValue(value), outputStream);
            }
        }
    }

    protected void writeObjectData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        getSharedObject().addSharedObject(value);
        writeCustomClassData(value, outputStream);
    }
}