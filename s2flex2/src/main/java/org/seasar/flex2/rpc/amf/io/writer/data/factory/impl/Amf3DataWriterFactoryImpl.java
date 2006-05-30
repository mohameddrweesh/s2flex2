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
package org.seasar.flex2.rpc.amf.io.writer.data.factory.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.io.ByteArray;
import org.seasar.flex2.io.external.Externalizable;
import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.flex2.rpc.amf.data.AmfDataType;
import org.seasar.flex2.rpc.amf.io.writer.data.Amf3DataWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.AmfDataWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.factory.Amf3DataWriterFactory;
import org.w3c.dom.Document;

public class Amf3DataWriterFactoryImpl implements Amf3DataWriterFactory {

    private HashMap amf3DataWriterMap;

    private HashMap writerMap;

    public Amf3DataWriter createDataValueWriter( final Object value) {
        return (Amf3DataWriter) amf3DataWriterMap.get(getAmf3DataType(value));
    }

    public AmfDataWriter createDataWriter( final Object value) {
        AmfDataWriter writer;
        String dataType = getAmf0DataType(value);
        if( dataType != null ){
            writer = (AmfDataWriter) writerMap.get(dataType);
        } else {
            writer = createDataValueWriter( value );
        }

        return writer;
    }

    public void setAmf3DataWriterMap(HashMap dataWriterMap) {
        this.amf3DataWriterMap = dataWriterMap;
    }

    public void setWriterMap(HashMap writerMap) {
        this.writerMap = writerMap;
    }

    private final String getAmf0DataType(final Object value) {
        String dataType = null;
        do {
            if (value == null) {
                dataType = AmfDataType.TYPE_NULL;
                break;
            }
            if (value instanceof String ) {
                dataType = AmfDataType.TYPE_STRING;
                break;
            }
            if (value instanceof BigDecimal) {
                dataType = BigDecimal.class.getName();
                break;
            }
            if (value instanceof Number) {
                dataType = AmfDataType.TYPE_NUMBER;
                break;
            }
            if (value instanceof Boolean) {
                dataType = AmfDataType.TYPE_BOOLEAN;
                break;
            }
            
        } while (false);
        
        return dataType;
    }

    private final String getAmf3DataType( final Object value) {
        String dataType = Amf3DataType.TYPE_NULL;
        do {
            if (value == null) {
                break;
            }
            if (value instanceof Integer) {
                int data = ((Integer) value).intValue();
                if (
                        data <= Amf3Constants.INTEGRR_MAX &&
                        data >= Amf3Constants.INTEGRR_MIN
                ) {
                    dataType = Amf3DataType.TYPE_INTEGER;
                    break;
                }
            }
            if (value instanceof String) {
                dataType = Amf3DataType.TYPE_STRING;
                break;
            }
            if (value instanceof Boolean) {
                dataType = Amf3DataType.TYPE_BOOLEAN;
                break;
            }
            if (value instanceof Date) {
                dataType = Amf3DataType.TYPE_DATE;
                break;
            }
            if (value instanceof BigDecimal) {
                dataType = BigDecimal.class.getName();
                break;
            }
            if (value instanceof Number) {
                dataType = Amf3DataType.TYPE_NUMBER;
                break;
            }
            if (value instanceof Object[]) {
                dataType = Amf3DataType.TYPE_ARRAY;
                break;
            }
            if (value instanceof Externalizable) {
                dataType = Externalizable.class.getName();
                break;
            }
            if (value instanceof Map) {
                dataType = Map.class.getName();
                break;
            }
            if (value instanceof Collection) {
                dataType = Collection.class.getName();
                break;
            }
            if (value instanceof Iterator) {
                dataType = Iterator.class.getName();
                break;
            }
            if (value instanceof Document) {
                dataType = Amf3DataType.TYPE_XML;
                break;
            }
            if (value instanceof ByteArray) {
                dataType = Amf3DataType.TYPE_BYTEARRAY;
                break;
            }
            if (
                    value.getClass() == Byte.class ||
                    value.getClass() == Short.class
            ) {
                dataType = Amf3DataType.TYPE_INTEGER;
                break;
            }
            dataType = Amf3DataType.TYPE_OBJECT;
        } while (false);
        
        return dataType;
    }
}