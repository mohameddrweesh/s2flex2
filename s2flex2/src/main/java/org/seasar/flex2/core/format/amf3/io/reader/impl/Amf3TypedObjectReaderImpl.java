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
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.reader.binder.DataBinder;
import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.io.reader.TypedObjectReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public class Amf3TypedObjectReaderImpl extends
        AbstractAmf3TypedObjectReaderImpl implements TypedObjectReader {

    private DataBinder[] dataBinders;

    public Object read(final DataInputStream inputStream) throws IOException {
        return null;
    }

    public final Object read(final int objectDef, final Class clazz,
            final DataInputStream inputStream) throws IOException {

        final Object object = ClassUtil.newInstance(clazz);
        addObjectReference(object);

        final String[] propertyNames = readClassProperties(objectDef, clazz,
                inputStream);

        final Object[] propertyValues = readPropertyValues(propertyNames,
                inputStream);

        setupProperties(object, propertyNames, propertyValues);

        return object;
    }

    public void setDataBinders(final DataBinder[] dataBinders) {
        this.dataBinders = dataBinders;
    }

    protected Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return null;
    }

    protected Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return null;
    }

    private final String[] readClassProperties(final int objectDef,
            final Class clazz, final DataInputStream inputStream)
            throws IOException {

        final String[] propertyNames;
        switch (objectDef & Amf3Constants.CLASS_DEF_INLINE) {

            case Amf3Constants.CLASS_DEF_INLINE:
                propertyNames = readPropertyNames(objectDef, clazz, inputStream);
                break;

            default:
                propertyNames = getPropertiesOf(clazz);

        }
        return propertyNames;
    }

    private final String[] readPropertyNames(final int objectDef,
            final Class clazz, final DataInputStream inputStream)
            throws IOException {
        final int propertyNumber = objectDef >>> 4;
        final String[] propertyNames = new String[propertyNumber];
        for (int i = 0; i < propertyNumber; i++) {
            propertyNames[i] = (String) amf3StringReader.read(inputStream);
        }
        addClassProperties(clazz, propertyNames);

        return propertyNames;
    }

    private final Object[] readPropertyValues(final String[] propertyNames,
            final DataInputStream inputStream) throws IOException {
        final Object[] propertyValues = new Object[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            propertyValues[i] = readPropertyValue(inputStream);
        }
        return propertyValues;
    }

    private final void setupProperties(final Object object,
            final String[] propertyNames, final Object[] propertyValues) {
        final int propertiesNumber = propertyNames.length;
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(object.getClass());

        PropertyDesc propertyDef;
        for (int i = 0; i < propertiesNumber; i++) {
            if (beanDesc.hasPropertyDesc(propertyNames[i])) {
                propertyDef = beanDesc.getPropertyDesc(propertyNames[i]);
                if (propertyDef.isWritable()) {
                    setupProperty(object, propertyDef, propertyValues[i]);
                }
            }
        }
    }

    private final void setupProperty(final Object object,
            final PropertyDesc propertyDef, final Object propertyValue) {
        if (propertyValue != null) {
            Object setValue = propertyValue;
            DataBinder binder;
            for (int i = 0; i < dataBinders.length; i++) {
                binder = dataBinders[i];
                if (binder.isTarget(propertyValue, propertyDef
                        .getPropertyType())) {
                    setValue = binder.bind(propertyValue, propertyDef
                            .getPropertyType());
                    break;
                }
            }
            propertyDef.setValue(object, setValue);
        }
    }
}