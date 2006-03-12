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

    public static void setProperties(Object object, int props,
            String[] names, Object[] values) {
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

    public static byte[] toBytesUTF8(String str) {
        try {
            return str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    public static Date toDate(double ms) {
        Date date = new Date((long) ms);
        return date;
    }

    public static Integer toInteger(int[] list, int bytes) {

        int int_data = 0;
        int offset = 0;
        for (int i = list.length; i > 0; i--) {
            if (list[i - 1] != 0x00) {
                offset = (bytes - i) * 7;
                int_data |= (list[i - 1] << offset);
            }
        }
        return new Integer(int_data);
    }

    public static String toStringUTF8(byte[] bytearr, int utflen) {
        try {
            return new String(bytearr, 0, utflen, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static int[] toVariableBytes(Integer value) {
        int[] list = new int[4];

        for (int i = 0; i < list.length; i++) {
            list[i] = value & 0x7F;
            value = value >>> 7;
            if (value <= 0) {
                break;
            }
        }
        return list;
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
