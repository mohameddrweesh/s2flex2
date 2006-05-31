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
package org.seasar.flex2.message.format.amf.io.writer.factory.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.message.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.message.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.message.format.amf.io.writer.impl.amf.AmfNullWriterImpl;
import org.w3c.dom.Document;

public class AmfDataWriterFactoryImpl implements AmfDataWriterFactory {

    protected HashMap writerMap;

    public void setWriterMap(HashMap writerMap) {
        this.writerMap = writerMap;
    }

    public AmfDataWriter createDataWriter(Object value) {

        AmfDataWriter writer = null;
        do {
            if (value == null) {
                writer = new AmfNullWriterImpl();
                break;
            }
            if (value instanceof String) {
                writer = (AmfDataWriter) writerMap.get(String.class);
                break;
            }
            if (value instanceof BigDecimal) {
                writer = (AmfDataWriter) writerMap.get(BigDecimal.class);
                break;
            }
            if (value instanceof Number) {
                writer = (AmfDataWriter) writerMap.get(Number.class);
                break;
            }
            if (value instanceof Boolean) {
                writer = (AmfDataWriter) writerMap.get(Boolean.class);
                break;
            }
            if (value instanceof Date) {
                writer = (AmfDataWriter) writerMap.get(Date.class);
                break;
            }
            if (value instanceof Object[]) {
                writer = (AmfDataWriter) writerMap.get(Array.class);
                break;
            }
            if (value instanceof Iterator) {
                writer = (AmfDataWriter) writerMap.get(Iterator.class);
                break;
            }
            if (value instanceof Collection) {
                writer = (AmfDataWriter) writerMap.get(Collection.class);
                break;
            }
            if (value instanceof Map) {
                writer = (AmfDataWriter) writerMap.get(Map.class);
                break;
            }
            if (value instanceof Document) {
                writer = (AmfDataWriter) writerMap.get(Document.class);
                break;
            }

            writer = (AmfDataWriter) writerMap.get(Class.class);
        } while (false);

        return writer;
    }
}