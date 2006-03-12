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
package org.seasar.flex2.rpc.amf.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.rpc.amf.AmfMessage;
import org.seasar.flex2.rpc.amf.AmfWriter;
import org.seasar.flex2.rpc.amf.type.Amf3DataType;
import org.seasar.flex2.rpc.amf.util.Amf3DataUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.w3c.dom.Document;

public class Amf3WriterImpl extends AmfWriterImpl implements AmfWriter {

    private Amf3References references;

    public Amf3WriterImpl(DataOutputStream outputStream, AmfMessage message) {
        super(outputStream, message);
    }

    public void write() throws IOException {
        writeVersion();
        writeHeader();
        writeBodies();
    }

    protected void initializeSharedObject() {
        super.initializeSharedObject();
        initializeReferences();
    }

    protected void writeArray(Object[] array) throws IOException {
        writeAMF3DataMaker();
        writeArrayData(array);
    }

    protected void writeBoolean(Boolean value) throws IOException {
        if (value.booleanValue()) {
            outputStream.writeByte(Amf3DataType.BOOLEAN_TRUE);
        } else {
            outputStream.writeByte(Amf3DataType.BOOLEAN_FALSE);
        }
    }

    protected void writeCustomClass(Object object) throws IOException {
        writeAMF3DataMaker();
        writeCustomClassData(object);
    }

    protected void writeData(Object value) throws IOException {
        if (value == null) {
            super.writeNull();
        } else {
            // AMF 0 Data
            if (value instanceof String) {
                super.writeString((String) value);
            } else if (value instanceof BigDecimal) {
                super.writeString(value.toString());
            } else if (value instanceof Number) {
                super.writeNumber((Number) value);
            } else if (value instanceof Boolean) {
                super.writeBoolean((Boolean) value);
            } else {
                // AMF3 Data
                if (value instanceof Date) {
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
        }
    }

    protected void writeDate(Date date) throws IOException {
        writeAMF3DataMaker();
        writeDateData(date);
    }

    protected void writeHeader() throws IOException {
        outputStream.writeShort(0x00);
    }

    protected void writeInteger(Integer value) throws IOException {
        outputStream.writeByte(Amf3DataType.INTEGER);
        writeIntegerData(value);
    }

    protected void writeNull() throws IOException {
        outputStream.writeByte(Amf3DataType.NULL);
    }

    protected void writeNumber(Number value) throws IOException {
        outputStream.writeByte(Amf3DataType.NUMBER);
        outputStream.writeDouble(value.doubleValue());
    }

    protected void writeObject(Map value) throws IOException {
        writeAMF3DataMaker();
        writeObjectData(value);
    }

    protected void writeString(String value) throws IOException {
        outputStream.writeByte(Amf3DataType.STRING);
        int index = references.getStringReferenceIndex(value);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }

        references.addStringReference(value);
        writeStringModifiedUTF8(value);
    }

    protected final void writeVersion() throws IOException {
        outputStream.writeShort(message.getVersion());
    }

    protected void writeXML(Document document) throws IOException {
        writeAMF3DataMaker();
        writeXMLData(document);
    }

    private final void initializeReferences() {
        super.initializeSharedObject();
        references = new Amf3References();
    }

    private final void writeAMF3Data(Object value) throws IOException {
        if (value == null) {
            writeNull();
        } else if (value instanceof String) {
            writeString((String) value);
        } else if (value instanceof BigDecimal) {
            writeString(value.toString());
        } else if (value instanceof Integer
                && Integer.bitCount(((Integer) value).intValue()) < 32) {
            writeInteger((Integer) value);
        } else if (value instanceof Number) {
            writeNumber((Number) value);
        } else if (value instanceof Boolean) {
            writeBoolean((Boolean) value);
        } else {
            // Object Data
            if (value instanceof Date) {
                writeDateData((Date) value);
            } else if (value instanceof Object[]) {
                writeArrayData((Object[]) value);
            } else if (value instanceof Iterator) {
                writeIterator((Iterator) value);
            } else if (value instanceof Collection) {
                writeCollection((Collection) value);
            } else if (value instanceof Map) {
                writeObjectData((Map) value);
            } else if (value instanceof Document) {
                writeXMLData((Document) value);
            } else {
                writeCustomClassData(value);
            }
        }
    }

    private final void writeAMF3DataMaker() throws IOException {
        outputStream.writeByte(Amf3DataType.AMF3_DATA_MARKER);
    }

    private final void writeArrayData(Object[] array) throws IOException {
        outputStream.writeByte(Amf3DataType.ARRAY);
        int index = references.getObjectReferenceIndex(array);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }

        references.addObjectReference(array);
        outputStream.writeByte(array.length << 1 | Amf3DataType.OBJECT_INLINE);
        outputStream.writeByte(Amf3DataType.EMPTY_STRING);
        for (int i = 0; i < array.length; i++) {
            writeAMF3Data(array[i]);
        }
    }

    private final void writeCustomClassData(Object object) throws IOException {
        outputStream.writeByte(Amf3DataType.OBJECT);

        int index = references.getObjectReferenceIndex(object);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }
        references.addObjectReference(object);
        writeObjectClassDef(object);

        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = (PropertyDesc) beanDesc.getPropertyDesc(i);
            if (pd.hasReadMethod()) {
                Object value = pd.getValue(object);
                writeAMF3Data(value);
            }
        }
    }

    private final void writeDateData(Date date) throws IOException {
        outputStream.writeByte(Amf3DataType.DATE);
        int index = references.getObjectReferenceIndex(date);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }

        references.addObjectReference(date);
        outputStream.writeByte(Amf3DataType.OBJECT_INLINE);
        outputStream.writeDouble(((Date) date).getTime());
    }

    private void writeIntegerData(Integer value) throws IOException {
        int[] list = Amf3DataUtil.toVariableBytes(value);
        for (int i = list.length - 1; i >= 1; i--) {
            if (list[i] != 0x00) {
                outputStream
                        .writeByte(Amf3DataType.INTEGER_DEMILITER | list[i]);
            }
        }
        outputStream.writeByte(list[0]);
    }


    private final void writeStringModifiedUTF8(String str) throws IOException {

        byte[] bytearr = Amf3DataUtil.toBytesUTF8(str);
        writeIntegerData((bytearr.length << 1) | Amf3DataType.OBJECT_INLINE);

        if( bytearr.length > 0 ){
            outputStream.write(bytearr, 0, bytearr.length);
        }
    }

    private void writeObjectClassDef(Object object) throws IOException {

        int class_index = references.getClassReferenceIndex(object.getClass());
        int int_data = Amf3DataType.OBJECT_INLINE;

        if (class_index >= 0) {
            int_data = class_index << 2 | int_data;
            outputStream.writeByte(int_data);
            return;
        }

        references.addClassReference(object.getClass());

        if (object instanceof Map) {
            int_data |= Amf3DataType.CLASS_DEF_INLINE;
            int_data |= Amf3DataType.OBJECT_DESERIALIZED;
            outputStream.writeByte(int_data);
            outputStream.writeByte(Amf3DataType.EMPTY_STRING);
        } else {
            BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());

            int_data |= Amf3DataType.CLASS_DEF_INLINE;
            int_data = (beanDesc.getPropertyDescSize() << 4) | int_data;
            outputStream.writeByte(int_data);
            String type = object.getClass().getName();
            writeTypeString(type);
            for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
                PropertyDesc pd = (PropertyDesc) beanDesc.getPropertyDesc(i);
                if (pd.hasReadMethod()) {
                    writeTypeString(pd.getPropertyName());
                }
            }
        }
    }

    private final void writeObjectData(Map value) throws IOException {
        outputStream.writeByte(Amf3DataType.OBJECT);

        int index = references.getObjectReferenceIndex(value);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }

        references.addObjectReference(value);
        writeObjectClassDef(value);

        for (Iterator i = value.entrySet().iterator(); i.hasNext();) {
            Map.Entry e = (Map.Entry) i.next();
            String propertyName = String.valueOf(e.getKey());
            writeTypeString(propertyName);
            writeAMF3Data(e.getValue());
        }

        outputStream.writeByte(Amf3DataType.EMPTY_STRING);
    }

    private void writeReferenceIndex(int index) throws IOException {
        outputStream.writeByte(index << 1);
    }

    private final void writeTypeString(String propertyName)
            throws IOException {
        references.addStringReference(propertyName);
        writeStringModifiedUTF8(propertyName);
    }

    private final void writeXMLData(Document document) throws IOException {
        outputStream.writeByte(Amf3DataType.XML);
        int index = references.getObjectReferenceIndex(document);
        if (index >= 0) {
            writeReferenceIndex(index);
            return;
        }
        references.addObjectReference(document);
        writeStringModifiedUTF8(Amf3DataUtil.toXmlString(document));
    }
}