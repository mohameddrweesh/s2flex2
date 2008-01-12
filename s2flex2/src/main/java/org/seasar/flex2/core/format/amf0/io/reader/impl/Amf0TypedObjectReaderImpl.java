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
package org.seasar.flex2.core.format.amf0.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public class Amf0TypedObjectReaderImpl extends
        AbstractAmf0TypedObjectReaderImpl {

    private static final Object createCustomObject(
            final DataInputStream inputStream) throws IOException {
        final Class clazz = ClassUtil.forName(inputStream.readUTF());
        return ClassUtil.newInstance(clazz);
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readCustomClass(inputStream);
    }

    private final Object readCustomClass(final DataInputStream inputStream)
            throws IOException {
        final Object bean = createCustomObject(inputStream);
        addSharedObject(bean);
        readProperties(inputStream, bean);
        return bean;
    }

    private final void readProperties(final DataInputStream inputStream,
            final Object bean) throws IOException {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        while (true) {
            final String key = inputStream.readUTF();
            final byte dataType = inputStream.readByte();
            if (dataType == Amf0TypeDef.EOO) {
                break;
            }
            if (beanDesc.hasPropertyDesc(key)) {
                final PropertyDesc pd = beanDesc.getPropertyDesc(key);
                if (pd.isWritable()) {
                    setupProperty(bean, pd, readData(dataType, inputStream));
                }
            }
        }
    }
}