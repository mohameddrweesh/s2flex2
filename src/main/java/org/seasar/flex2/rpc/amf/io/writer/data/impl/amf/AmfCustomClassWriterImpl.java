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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.io.writer.data.AmfDataWriter;
import org.seasar.flex2.rpc.amf.type.AmfDataType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class AmfCustomClassWriterImpl extends AbstractAmfObjectWriterImpl
        implements AmfDataWriter {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        int index = getSharedObject().getSharedIndex(value);
        if (index >= 0) {
            writeSharedIndex(index, outputStream);
            return;
        }
        getSharedObject().addSharedObject(value);
        String type = value.getClass().getName();
        outputStream.writeByte(AmfDataType.CUSTOM_CLASS);
        outputStream.writeUTF(type);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = (PropertyDesc) beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod()) {
                outputStream.writeUTF(pd.getPropertyName());
                writeData(pd.getValue(value), outputStream);
            }
        }
        outputStream.writeShort(0);
        outputStream.writeByte(9);
    }
}