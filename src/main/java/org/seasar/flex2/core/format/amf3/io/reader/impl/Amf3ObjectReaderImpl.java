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

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf.type.AmfObject;
import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.io.DataInput;
import org.seasar.flex2.core.format.amf3.io.Externalizable;
import org.seasar.flex2.core.format.amf3.io.factory.ExternalizeDataInputFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public class Amf3ObjectReaderImpl extends AbstractAmf3TypedObjectReaderImpl {

    private ExternalizeDataInputFactory externalizeDataInputFactory;

    private AmfDataReader stringReader;

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    public void setExternalizeDataInputFactory(
            ExternalizeDataInputFactory dataInputFactory) {
        this.externalizeDataInputFactory = dataInputFactory;
    }

    public void setStringReader(AmfDataReader stringReader) {
        this.stringReader = stringReader;
    }

    private Object createClassInstance(final Class clazz) {
        final Object object = ClassUtil.newInstance(clazz);
        addObjectReference(object);
        return object;
    }

    private final Object readASObjectData(final DataInputStream inputStream)
            throws IOException {

        final AmfObject object = new AmfObject();
        addObjectReference(object);
        while (true) {
            String propertyName = (String) stringReader.read(inputStream);
            if (propertyName.length() <= 0) {
                break;
            }
            object.put(propertyName, readPropertyValue(inputStream));
        }
        return object;
    }

    private final Class readClassDef(final int objectDef,
            final DataInputStream inputStream) throws IOException {

        Class clazz = AmfObject.class;

        switch (objectDef & Amf3Constants.CLASS_DEF_INLINE) {

            case Amf3Constants.CLASS_DEF_REFERENCE:
                clazz = getClassAt(objectDef >>> 2);
                break;

            case Amf3Constants.CLASS_DEF_INLINE:
                String className = (String) stringReader.read(inputStream);
                if (className.length() > 0) {
                    clazz = ClassUtil.forName(className);
                }
                addClassReference(clazz);
                break;
        }

        return clazz;
    }

    private final Object readClassObjectData(final int objectDef,
            final Class clazz, final DataInputStream inputStream)
            throws IOException {

        final Object object = createClassInstance(clazz);

        final String[] propertyNames = readClassProperties(objectDef, clazz,
                inputStream);

        final Object[] propertyValues = readPropertyValues(propertyNames,
                inputStream);

        setupObjectProperties(object, propertyNames, propertyValues);

        return object;
    }

    private final String[] readClassProperties(final int objectDef,
            final Class clazz, final DataInputStream inputStream)
            throws IOException {

        final String[] propertyNames;
        switch (objectDef & Amf3Constants.CLASS_DEF_INLINE) {

            case Amf3Constants.CLASS_DEF_INLINE:
                propertyNames = readProperties(objectDef, clazz, inputStream);
                break;

            default:
                propertyNames = getPropertiesOf(clazz);

        }
        return propertyNames;
    }

    private final Object readExternalizableObjectData(int objectDef,
            Class clazz, DataInputStream inputStream) throws IOException {
        final Externalizable externalizable = (Externalizable) createClassInstance(clazz);
        final DataInput input = externalizeDataInputFactory
                .createDataIpput(inputStream);
        externalizable.readExternal(input);

        return externalizable;
    }

    private final Object readObjectData(final int objectDef,
            final DataInputStream inputStream) throws IOException {

        final Class clazz = readClassDef(objectDef, inputStream);

        Object object = null;
        do {

            if (clazz == null) {
                break;
            }

            if (clazz == AmfObject.class) {
                object = readASObjectData(inputStream);
                break;
            }

            if (Externalizable.class.isAssignableFrom(clazz)) {
                object = readExternalizableObjectData(objectDef, clazz,
                        inputStream);
                break;
            }

            object = readClassObjectData(objectDef, clazz, inputStream);
        } while (false);

        return object;
    }

    private final String[] readProperties(final int objectDef,
            final Class clazz, final DataInputStream inputStream)
            throws IOException {
        final int propertyNumber = objectDef >>> 4;
        final String[] propertyNames = new String[propertyNumber];
        for (int i = 0; i < propertyNumber; i++) {
            propertyNames[i] = (String) stringReader.read(inputStream);
        }
        addClassProperties(clazz, propertyNames);

        return propertyNames;
    }

    private final Object readPropertyValue(final DataInputStream inputStream)
            throws IOException {
        final byte dataType = inputStream.readByte();
        return writeElementData(dataType, inputStream);
    }

    private final Object[] readPropertyValues(final String[] propertyNames,
            final DataInputStream inputStream) throws IOException {
        final Object[] propertyValues = new Object[propertyNames.length];
        for (int i = 0; i < propertyNames.length; i++) {
            propertyValues[i] = readPropertyValue(inputStream);
        }
        return propertyValues;
    }

    private final void setupObjectProperties(final Object object,
            final String[] propertyNames, final Object[] propertyValues) {
        final int propertiesNumber = propertyNames.length;
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        
        PropertyDesc propertyDef;
        for (int i = 0; i < propertiesNumber; i++) {
            if (beanDesc.hasPropertyDesc(propertyNames[i])) {
                propertyDef = beanDesc.getPropertyDesc(propertyNames[i]);
                if (propertyDef.hasWriteMethod()) {
                    propertyDef.setValue(object, propertyValues[i]);
                }
            }
        }
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readObjectData(reference, inputStream);
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }
}