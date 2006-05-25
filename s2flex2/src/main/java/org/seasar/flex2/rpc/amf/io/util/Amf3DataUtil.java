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
package org.seasar.flex2.rpc.amf.io.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Amf3DataUtil {

    private final static String STRING_ENCODE_NAME = "utf-8";

    public final static int getIntByteLength(int value) {
        if (value < 0) {
            return 4;
        }
        if (value >= 0x10000000) {
            return -1;
        }
        if (value >= 0x200000) {
            return 4;
        }
        if (value >= 0x4000) {
            return 3;
        }
        if (value >= 0x80) {
            return 2;
        }
        if (value >= 0x0) {
            return 1;
        }
        return -1;
    }

    public final static void setProperties(Object object,
            String[] propertyNames, Object[] propertyValues) {
        int propertiesNumber = propertyNames.length;
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        PropertyDesc propertyDef;
        for (int i = 0; i < propertiesNumber; i++) {
            if (beanDesc.hasPropertyDesc(propertyNames[i])) {
                propertyDef = beanDesc
                        .getPropertyDesc(propertyNames[i]);
                if (propertyDef.hasWriteMethod()) {
                    propertyDef.setValue(object, propertyValues[i]);
                }
            }
        }
    }

    public final static Date toDate(double ms) {
        Date date = new Date((long) ms);
        return date;
    }

    public final static int toInt(int[] list, int bytes) {
        int intValue = list[bytes - 1];
        int offset = 0;

        if (bytes < Amf3Constants.INTEGER_DATA_MAX_BYTES) {
            offset = 7;
        } else {
            offset = 8;
        }

        for (int i = bytes - 1; i > 0; i--) {
            intValue |= (list[i - 1] << offset);
            offset += 7;
        }

        return intValue;
    }

    public final static int[] toVariableIntBytes( final int value) {
        int intByteLength = Amf3DataUtil.getIntByteLength(value);
        if (intByteLength < 0) {
            return new int[0];
        }

        int[] list = new int[intByteLength];
        int intValue = value;

        if (intByteLength < 4) {
            list[0] = intValue & 0x7F;
            intValue = intValue >>> 7;
        } else {
            list[0] = intValue & 0xFF;
            intValue = intValue >>> 8;
        }

        for (int i = 1; i < intByteLength; i++) {
            list[i] = intValue & 0x7F;
            intValue = intValue >>> 7;
        }
        return list;
    }

    public final static int toNegativeInt(int[] list, int bytes) {
        return toInt(list, bytes) | 0xF0000000;
    }

    public final static int[] toNegativeIntBytes(int value) {
        return toVariableIntBytes(value);
    }

    public final static String toUTF8String(byte[] bytearr, int utflen) {
        try {
            return new String(bytearr, 0, utflen, STRING_ENCODE_NAME);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public final static byte[] toUTF8StringBytes(String str) {
        try {
            return str.getBytes(STRING_ENCODE_NAME);
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    public final static Document toXmlDocument(String xml) {
        ByteArrayInputStream bain;
        try {
            bain = new ByteArrayInputStream(xml.getBytes(STRING_ENCODE_NAME));
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(bain);
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        return DocumentBuilderUtil.parse(builder, bis);
    }

    public final static String toXmlString(Document document) {
        Element element = document.getDocumentElement();
        return DomUtil.toString(element);
    }
}
