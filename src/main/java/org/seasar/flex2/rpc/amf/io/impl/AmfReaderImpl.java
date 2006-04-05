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
package org.seasar.flex2.rpc.amf.io.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfConstants;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;
import org.seasar.flex2.rpc.amf.io.AmfReader;
import org.seasar.flex2.rpc.amf.type.AmfDataType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.w3c.dom.Document;

import flashgateway.io.ASObject;

public class AmfReaderImpl implements AmfReader {

    protected static final Logger logger = Logger.getLogger(AmfReaderImpl.class);

    protected DataInputStream inputStream;

    protected AmfMessage message = createMessage();

    protected List sharedObjects;

    public AmfReaderImpl(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public AmfMessage read() throws IOException {
        skipHeaders();
        readBodies();
        return message;
    }

    protected AmfMessage createMessage() {
        return new AmfMessageImpl();
    }

    protected AmfBody createBody(String target, String response, Object data) {
        return new AmfBodyImpl(target, response, data);
    }

    protected void skipHeaders() throws IOException {
        inputStream.readUnsignedShort();
        int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            inputStream.readUTF();
            readBoolean();
            inputStream.readInt();
            readData();
        }
    }

    protected void readBodies() throws IOException {
        int bodySize = inputStream.readUnsignedShort();
        for (int i = 0; i < bodySize; ++i) {
            initializeSharedObject();
            String target = inputStream.readUTF();
            String response = inputStream.readUTF();
            inputStream.readInt();
            Object data = readData();
            message.addBody(createBody(target, response, data));
        }
    }

    protected void initializeSharedObject() {
        sharedObjects = new ArrayList();
    }

    protected Object readData() throws IOException {
        byte dataType = inputStream.readByte();
        return readData(dataType);
    }

    protected Object readData(byte dataType) throws IOException {
        switch (dataType) {
            case AmfDataType.NUMBER:
                return readNumber();
            case AmfDataType.BOOLEAN:
                return readBoolean();
            case AmfDataType.STRING:
                return readString();
            case AmfDataType.DATE:
                return readDate();
            case AmfDataType.XML:
                return readXML();
            case AmfDataType.NULL:
                return readNull();
            case AmfDataType.ARRAY_SHARED_OBJECT:
                return readArraySharedObject();
            case AmfDataType.AS_OBJECT:
                return readASObject();
            case AmfDataType.OBJECT:
                return readObject();
            case AmfDataType.CUSTOM_CLASS:
                return readCustomClass();
            case AmfDataType.ARRAY:
                return readArray();
            case AmfDataType.FLASHED_SHARED_OBJECT:
                return readFlashedSharedObject();
            default:
                return null;
        }
    }

    protected Double readNumber() throws IOException {
        double d = inputStream.readDouble();
        logger.debug("readNumber:" + d);
        return new Double(d);
    }

    protected Boolean readBoolean() throws IOException {
        byte value = inputStream.readByte();
        logger.debug("readBoolean:" + value);
        if (value == 1) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    protected String readString() throws IOException {
        String s = inputStream.readUTF();
        logger.debug("readString:" + s);
        return s;
    }

    protected Date readDate() throws IOException {
        double ms = inputStream.readDouble();
        int offset = inputStream.readUnsignedShort()
                * AmfConstants.MILLS_PER_HOUR;
        int defaultOffset = TimeZone.getDefault().getRawOffset();
        ms += (double) defaultOffset - offset;
        Date date = new Date((long) ms);
        logger.debug("readDate:" + date);
        return date;
    }

    protected Document readXML() throws IOException {
        logger.debug("readXML:");
        inputStream.skip(4);
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        return DocumentBuilderUtil.parse(builder, inputStream);
    }

    protected Object readNull() throws IOException {
        logger.debug("readNull:");
        return null;
    }

    protected Object readArraySharedObject() throws IOException {
        logger.debug("readArraySharedObject:");
        return null;
    }

    protected Object readASObject() throws IOException {
        logger.debug("readASObject:");
        return null;
    }

    protected Object readObject() throws IOException {
        logger.debug("readObject:");
        ASObject obj = new ASObject();
        addSharedObject(obj);
        while (true) {
            String key = inputStream.readUTF();
            byte dataType = inputStream.readByte();
            if (dataType == AmfDataType.EOM) {
                break;
            }
            Object value = readData(dataType);
            logger.debug("property=" + key + ",value=" + value);
            obj.put(key, value);
        }
        if (obj.containsKey(AmfConstants.REMOTE_CLASS)) {
            return translateBean(obj);
        } else {
            return obj;
        }
    }

    protected Object translateBean(ASObject asObject) {
        String type = (String) asObject.get(AmfConstants.REMOTE_CLASS);
        Class clazz = ClassUtil.forName(type);
        Object bean = ClassUtil.newInstance(clazz);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        for (Iterator i = asObject.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (beanDesc.hasPropertyDesc(key)) {
                Object value = asObject.get(key);
                PropertyDesc pd = beanDesc.getPropertyDesc(key);
                if (pd.hasWriteMethod()) {
                    pd.setValue(bean, value);
                }
            }
        }
        return bean;
    }

    protected Object readCustomClass() throws IOException {
        String type = inputStream.readUTF();
        logger.debug("readCustomClass:" + type);
        Class clazz = ClassUtil.forName(type);
        Object bean = ClassUtil.newInstance(clazz);
        addSharedObject(bean);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        while (true) {
            String key = inputStream.readUTF();
            byte dataType = inputStream.readByte();
            if (dataType == AmfDataType.EOM) {
                break;
            }
            Object value = readData(dataType);
            logger.debug("property=" + key + ",value=" + value);
            if (beanDesc.hasPropertyDesc(key)) {
                PropertyDesc pd = beanDesc.getPropertyDesc(key);
                if (pd.hasWriteMethod()) {
                    pd.setValue(bean, value);
                }
            }
        }
        return bean;
    }

    protected List readArray() throws IOException {
        ArrayList array = new ArrayList();
        addSharedObject(array);
        int length = inputStream.readInt();
        logger.debug("readArray:size=" + length);
        for (int i = 0; i < length; i++) {
            Object item = readData();
            logger.debug("item(" + i + ")=" + item);
            array.add(item);
        }
        return array;
    }

    protected Object readFlashedSharedObject() throws IOException {
        int index = inputStream.readUnsignedShort();
        Object target = sharedObjects.get(index);
        logger.debug("readFlashedSharedObject:index=" + index + ",value="
                + target);
        return target;
    }

    private void addSharedObject(Object o) {
        logger.debug("addSharedObject:index=" + sharedObjects.size()
                + ",value=" + o);
        sharedObjects.add(o);
    }
}