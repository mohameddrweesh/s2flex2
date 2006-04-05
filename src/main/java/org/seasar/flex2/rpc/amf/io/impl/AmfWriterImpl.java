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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfConstants;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.type.AmfDataType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AmfWriterImpl implements AmfWriter {

    protected DataOutputStream outputStream;

    protected AmfMessage message;

    protected ArrayList sharedObjects;

    public AmfWriterImpl(DataOutputStream outputStream, AmfMessage message) {
        this.outputStream = outputStream;
        this.message = message;
    }

    public void write() throws IOException {
        outputStream.writeShort(0);
        outputStream.writeShort(0);
        writeBodies();
    }

    protected void writeBodies() throws IOException {
        outputStream.writeShort(message.getBodySize());
        for (int i = 0; i < message.getBodySize(); ++i) {
            initializeSharedObject();
            writeBody(message.getBody(i));
        }
    }

    protected void initializeSharedObject() {
        sharedObjects = new ArrayList();
    }

    protected void writeBody(AmfBody body) throws IOException {
        outputStream.writeUTF(body.getTarget());
        outputStream.writeUTF(body.getResponse());
        outputStream.writeInt(-1);
        writeData(body.getData());
    }

    protected void writeData(Object value) throws IOException {
        if (value == null) {
            writeNull();
        } else if (value instanceof String) {
            writeString((String) value);
        } else if (value instanceof BigDecimal) {
            writeString(value.toString());
        } else if (value instanceof Number) {
            writeNumber((Number) value);
        } else if (value instanceof Boolean) {
            writeBoolean((Boolean) value);
        } else if (value instanceof Date) {
            writeDate((Date) value);
        } else if (value instanceof Object[]) {
            writeArray((Object[]) value);
        } else if (value instanceof Iterator) {
            writeIterator((Iterator) value);
        } else if (value instanceof Collection) {
            writeCollection((Collection) value);
        } else if (value instanceof Map) {
            writeObject((Map) value);
        } else if (value instanceof Document) {
            writeXML((Document) value);
        } else {
            writeCustomClass(value);
        }
    }

    protected void writeNull() throws IOException {
        outputStream.writeByte(AmfDataType.NULL);
    }

    protected void writeNumber(Number value) throws IOException {
        outputStream.writeByte(AmfDataType.NUMBER);
        outputStream.writeDouble(value.doubleValue());
    }

    protected void writeString(String value) throws IOException {
        outputStream.writeByte(AmfDataType.STRING);
        outputStream.writeUTF(value);
    }

    protected void writeBoolean(Boolean value) throws IOException {
        outputStream.writeByte(AmfDataType.BOOLEAN);
        outputStream.writeBoolean(value.booleanValue());
    }

    protected void writeFlashedSharedObject(int index) throws IOException {
        outputStream.writeByte(AmfDataType.FLASHED_SHARED_OBJECT);
        outputStream.writeShort(index);
    }

    protected void writeDate(Date value) throws IOException {
        outputStream.writeByte(AmfDataType.DATE);
        outputStream.writeDouble(((Date) value).getTime());
        int offset = TimeZone.getDefault().getRawOffset();
        outputStream.writeShort(offset / AmfConstants.MILLS_PER_HOUR);
    }

    protected void writeCustomClass(Object object) throws IOException {
        int index = getSharedIndex(object);
        if (index >= 0) {
            writeFlashedSharedObject(index);
            return;
        }
        addSharedObject(object);
        String type = object.getClass().getName();
        outputStream.writeByte(AmfDataType.CUSTOM_CLASS);
        outputStream.writeUTF(type);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = (PropertyDesc) beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod()) {
                Object value = pd.getValue(object);
                outputStream.writeUTF(pd.getPropertyName());
                writeData(value);
            }
        }
        outputStream.writeShort(0);
        outputStream.writeByte(9);
    }

    protected void writeArray(Object[] value) throws IOException {
        int index = getSharedIndex(value);
        if (index >= 0) {
            writeFlashedSharedObject(index);
            return;
        }
        addSharedObject(value);
        outputStream.writeByte(AmfDataType.ARRAY);
        outputStream.writeInt(value.length);
        for (int i = 0; i < value.length; i++) {
            writeData(value[i]);
        }
    }

    protected void writeIterator(Iterator value) throws IOException {
        ArrayList list = new ArrayList();
        while (value.hasNext()) {
            list.add(value.next());
        }
        writeArray(list.toArray(new Object[list.size()]));
    }

    protected void writeCollection(Collection value) throws IOException {
        ArrayList list = new ArrayList();
        list.addAll(value);
        writeArray(list.toArray(new Object[list.size()]));
    }

    protected void writeObject(Map value) throws IOException {
        int index = getSharedIndex(value);
        if (index >= 0) {
            writeFlashedSharedObject(index);
            return;
        }
        addSharedObject(value);
        outputStream.writeByte(AmfDataType.OBJECT);
        for (Iterator i = value.entrySet().iterator(); i.hasNext();) {
            Map.Entry e = (Map.Entry) i.next();
            String propertyName = String.valueOf(e.getKey());
            outputStream.writeUTF(propertyName);
            writeData(e.getValue());
        }
        outputStream.writeShort(0);
        outputStream.writeByte(9);
    }

    protected void writeXML(Document document) throws IOException {
        outputStream.writeByte(AmfDataType.XML);
        Element element = document.getDocumentElement();
        String xmlData = DomUtil.toString(element);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(xmlData.getBytes());
        outputStream.writeInt(baos.size());
        baos.writeTo(outputStream);
    }

    protected final void addSharedObject(Object o) {
        sharedObjects.add(o);
    }

    protected final int getSharedIndex(Object o) {
        for (int i = 0; i < sharedObjects.size(); ++i) {
            if (o == sharedObjects.get(i)) {
                return i;
            }
        }
        return -1;
    }
}