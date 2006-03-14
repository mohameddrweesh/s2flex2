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
package org.seasar.flex2.rpc.amf.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Amf3DataUtil {

    public static int getIntegerByteLength(Integer value) {
        if (value == null) {
            return 0;
        }
        if (value.intValue() < 0) {
            return 4;
        }
        if (value.intValue() >= 0x10000000) {
            return -1;
        }
        if (value.intValue() >= 0x200000) {
            return 4;
        }
        if (value.intValue() >= 0x4000) {
            return 3;
        }
        if (value.intValue() >= 0x80) {
            return 2;
        }
        if (value.intValue() >= 0x0) {
            return 1;
        }
        return -1;
    }

    public static void setProperties(Object object, int props, String[] names,
            Object[] values) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(object.getClass());
        for (int i = 0; i < props; i++) {
            if (beanDesc.hasPropertyDesc(names[i])) {
                PropertyDesc pd = beanDesc.getPropertyDesc(names[i]);
                if (pd.hasWriteMethod()) {
                    pd.setValue(object, values[i]);
                }
            }
        }
    }

    public static Date toDate(double ms) {
        Date date = new Date((long) ms);
        return date;
    }

    public static Integer toInteger(int[] list, int bytes) {
        int int_data = list[bytes - 1];
        int offset = 0;

        if (bytes < 4) {
            offset = 7;
        } else {
            offset = 8;
        }

        for (int i = bytes - 1; i > 0; i--) {
            int_data |= (list[i - 1] << offset);
            offset += 7;
        }

        return Integer.valueOf(int_data);
    }

    public static int[] toIntegerVariableBytes(Integer value) {
        int list_len = Amf3DataUtil.getIntegerByteLength(value);
        if( list_len < 0 ){
            return new int[0];
        }
        
        int[] list = new int[list_len];
        int intValue = value.intValue();

        if (list_len < 4) {
            list[0] = intValue & 0x7F;
            intValue = intValue >>> 7;
        } else {
            list[0] = intValue & 0xFF;
            intValue = intValue >>> 8;
        }

        for (int i = 1; i < list_len; i++) {
            list[i] = intValue & 0x7F;
            intValue = intValue >>> 7;
        }
        return list;
    }

    public static Integer toNegativeInteger(int[] list, int bytes) {
        return Integer.valueOf(toInteger(list, bytes).intValue() | 0xF0000000);
    }

    public static int[] toNegativeIntegerBytes(Integer value) {
        return toIntegerVariableBytes(value);
    }

    public static String toUTF8String(byte[] bytearr, int utflen) {
        try {
            return new String(bytearr, 0, utflen, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] toUTF8StringBytes(String str) {
        try {
            return str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    public static Document toXmlDocument(String xml) {
        ByteArrayInputStream bain;
        try {
            bain = new ByteArrayInputStream(xml.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        BufferedInputStream bis = new BufferedInputStream(bain);
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        return DocumentBuilderUtil.parse(builder, bis);
    }

    public static String toXmlString(Document document) {
        Element element = document.getDocumentElement();
        return DomUtil.toString(element);
    }
}
