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

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seasar.flex2.rpc.amf.AmfMessage;
import org.seasar.flex2.rpc.amf.AmfReader;
import org.seasar.flex2.rpc.amf.type.Amf3DataType;
import org.seasar.flex2.rpc.amf.type.AmfDataType;
import org.seasar.flex2.rpc.amf.util.Amf3DataUtil;
import org.seasar.framework.util.ClassUtil;
import org.w3c.dom.Document;

import flashgateway.io.ASObject;

public class Amf3ReaderImpl extends AmfReaderImpl implements AmfReader {

    private Amf3References references;

    public Amf3ReaderImpl(DataInputStream inputStream) {
        super(inputStream);
    }

    public AmfMessage read() throws IOException {
        readVersion();
        readHeader();
        readBodies();
        return message;
    }

    protected void initializeSharedObject() {
        super.initializeSharedObject();
        references = new Amf3References();
    }

    protected List readArray() throws IOException {
        int reference = readInteger();

        switch (reference & Amf3DataType.OBJECT_INLINE) {
            case Amf3DataType.OBJECT_REFERENCE:
                return references.getListAt(reference >>> 1);
            case Amf3DataType.OBJECT_INLINE:
                return readArrayData(reference);
        }
        return null;
    }

    protected Object readASObject() throws IOException {
        ASObject obj = new ASObject();
        references.addObjectReference(obj);
        while (true) {
            String prop_name = readString();
            if (prop_name.length() <= 0) {
                break;
            }
            //
            Object value = readAMF3Data();
            obj.put(prop_name, value);
        }
        return obj;
    }

    protected Boolean readBooleanFalse() {
        return Boolean.FALSE;
    }

    protected Boolean readBooleanTrue() {
        return Boolean.TRUE;
    }

    protected Object readCustomClass(int reference, Class clazz)
            throws IOException {
        Object object = ClassUtil.newInstance(clazz);
        references.addObjectReference(object);

        int props_num = 0;
        String[] prop_names = null;
        if ((reference & Amf3DataType.CLASS_DEF_INLINE) == Amf3DataType.CLASS_DEF_INLINE) {
            props_num = reference >>> 4;
            prop_names = new String[props_num];
            for (int i = 0; i < props_num; i++) {
                prop_names[i] = readString();
            }
            references.addProperties(prop_names);
        } else {
            prop_names = references.getPropertiesAt(reference >>> 2);
            props_num = prop_names.length;
        }

        Object[] prop_values = new Object[props_num];
        for (int i = 0; i < props_num; i++) {
            prop_values[i] = readAMF3Data();
        }

        Amf3DataUtil.setProperties(object, props_num, prop_names, prop_values);

        return object;
    }

    protected Object readData() throws IOException {
        byte dataType = inputStream.readByte();
        return readData(dataType);
    }

    protected Object readData(byte dataType) throws IOException {
        switch (dataType) {
            case AmfDataType.NULL:
                return super.readNull();
            case AmfDataType.NUMBER:
                return super.readNumber();
            case AmfDataType.BOOLEAN:
                return super.readBoolean();
            case AmfDataType.STRING:
                return super.readString();
            case AmfDataType.ARRAY:
                return super.readArray();

            case Amf3DataType.AMF3_DATA_MARKER:
                dataType = inputStream.readByte();
                return readAMF3Data(dataType);

            default:
                return null;
        }
    }

    protected Date readDate() throws IOException {
        int reference = readInteger();

        switch (reference & Amf3DataType.OBJECT_INLINE) {
            case Amf3DataType.OBJECT_REFERENCE:
                return references.getDateAt(reference >>> 1);
            case Amf3DataType.OBJECT_INLINE:
                return readDateData();
        }
        return null;
    }

    protected void readHeader() throws IOException {
        int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            inputStream.readUTF();
            readBoolean();
            inputStream.readInt();
            readData();
        }
    }

    protected Integer readInteger() throws IOException {
        int[] list = new int[4];
        int byte_count = 0;
        for (int i = 0; i < list.length; i++) {
            list[i] = inputStream.readByte();
            byte_count++;
            if ((list[i] >>> 7) == 0x00) {
                break;
            }
            list[i] &= 0x7F;
        }
        if (list[byte_count - 1] == 0x00) {
            return new Integer(0);
        } else {
            return Amf3DataUtil.toInteger(list, byte_count);
        }
    }

    protected Object readObject() throws IOException {
        int reference = readInteger();

        switch (reference & Amf3DataType.OBJECT_INLINE) {
            case Amf3DataType.OBJECT_REFERENCE:
                return references.getObjectAt(reference >>> 1);
            case Amf3DataType.OBJECT_INLINE:
                return readObjectData(reference);
        }
        return null;
    }

    protected String readString() throws IOException {
        int reference = readInteger();
        switch (reference & Amf3DataType.OBJECT_INLINE) {
            case Amf3DataType.OBJECT_REFERENCE:
                return references.getStringAt(reference >>> 1);
            case Amf3DataType.OBJECT_INLINE:
                return readStringData(reference);
        }
        return null;
    }

    protected void readVersion() throws IOException {
        message.setVersion(inputStream.readUnsignedShort());
    }

    protected Document readXML() throws IOException {
        int reference = readInteger();
        switch (reference & Amf3DataType.OBJECT_INLINE) {
            case Amf3DataType.OBJECT_REFERENCE:
                return references.getXmlDocumentAt(reference >>> 1);
            case Amf3DataType.OBJECT_INLINE:
                return readXmlData(reference);
        }
        return null;
    }

    private final Object readAMF3Data() throws IOException {
        byte dataType = inputStream.readByte();
        return readAMF3Data(dataType);
    }

    private final Object readAMF3Data(byte dataType) throws IOException {
        switch (dataType) {
            case Amf3DataType.NULL:
                return super.readNull();
            case Amf3DataType.NUMBER:
                return super.readNumber();
            case Amf3DataType.INTEGER:
                return readInteger();
            case Amf3DataType.BOOLEAN_FALSE:
                return readBooleanFalse();
            case Amf3DataType.BOOLEAN_TRUE:
                return readBooleanTrue();
            case Amf3DataType.STRING:
                return readString();
            case Amf3DataType.DATE:
                return readDate();
            case Amf3DataType.ARRAY:
                return readArray();
            case Amf3DataType.OBJECT:
                return readObject();
            case Amf3DataType.XML:
                return readXML();
            default:
                return null;
        }
    }

    private final List readArrayData(int reference) throws IOException {
        int array_length = reference >> 1;
        List<Object> array = new ArrayList<Object>(array_length);
        references.addObjectReference(array);
        int mark = inputStream.readByte();
        for (int i = 0; i < array_length; i++) {
            Object item = readAMF3Data();
            array.add(item);
        }
        return array;
    }

    private final Date readDateData() throws IOException {
        Date date = Amf3DataUtil.toDate(inputStream.readDouble());
        references.addObjectReference(date);
        return date;
    }

    private final Class readObjectClassDef(int reference) throws IOException {

        Class clazz = Object.class;

        switch (reference & Amf3DataType.CLASS_DEF_INLINE) {
            case Amf3DataType.CLASS_DEF_REFERENCE:
                clazz = references.getClassAt(reference >>> 2);
                break;
            case Amf3DataType.CLASS_DEF_INLINE:
                String class_name = readString();
                if (class_name.length() > 0) {
                    clazz = ClassUtil.forName(class_name);
                    references.addClassReference(clazz);
                }
                break;
        }
        return clazz;
    }

    private final Object readObjectData(int reference) throws IOException {
        Class clazz = readObjectClassDef(reference);
        if (clazz != null && !(Object.class.equals(clazz))) {
            return readCustomClass(reference, clazz);
        } else {
            return readASObject();
        }
    }

    private final String readStringData(int reference) throws IOException {
        int str_length = reference >> 1;
        String str = null;
        if (str_length > 0) {
            byte[] bytearr = new byte[str_length * 2];
            inputStream.readFully(bytearr, 0, str_length);
            str = Amf3DataUtil.toStringUTF8(bytearr, str_length);
            references.addStringReference(str);
        } else {
            str = "";
        }

        return str;
    }

    private final Document readXmlData(int reference) throws IOException {

        int str_length = reference >>> 1;
        byte[] bytearr = new byte[str_length * 2];
        inputStream.readFully(bytearr, 0, str_length);
        String xml_data = Amf3DataUtil.toStringUTF8(bytearr, str_length);

        Document xml = Amf3DataUtil.toXmlDocument(xml_data);
        references.addObjectReference(xml);

        return xml;
    }
}