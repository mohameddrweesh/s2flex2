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
package org.seasar.flex2.rpc.amf.io.writer.factory.impl;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.rpc.amf.io.writer.data.AmfObjectWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.impl.amf.AmfNullWriterImpl;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfObjectWriterFactory;
import org.w3c.dom.Document;

public class AmfObjectWriterFactoryImpl implements AmfObjectWriterFactory {

    protected HashMap writerMap;

    public AmfObjectWriterFactoryImpl() {
    }

    public void setWriterMap(HashMap writerMap) {
        this.writerMap = writerMap;
    }

    public AmfObjectWriter createObjectWriter(Object value) {

        AmfObjectWriter writer = null;
        do {
            if (value == null) {
                writer = new AmfNullWriterImpl();
                break;
            }
            if (value instanceof String) {
                writer = (AmfObjectWriter) writerMap.get(String.class);
                break;
            }
            if (value instanceof BigDecimal) {
                writer = (AmfObjectWriter) writerMap.get(BigDecimal.class);
                break;
            }
            if (value instanceof Number) {
                writer = (AmfObjectWriter) writerMap.get(Number.class);
                break;
            }
            if (value instanceof Boolean) {
                writer = (AmfObjectWriter) writerMap.get(Boolean.class);
                break;
            }
            if (value instanceof Date) {
                writer = (AmfObjectWriter) writerMap.get(Date.class);
                break;
            }
            if (value instanceof Object[]) {
                writer = (AmfObjectWriter) writerMap.get(Array.class);
                break;
            }
            if (value instanceof Iterator) {
                writer = (AmfObjectWriter) writerMap.get(Iterator.class);
                break;
            }
            if (value instanceof Collection) {
                writer = (AmfObjectWriter) writerMap.get(Collection.class);
                break;
            }
            if (value instanceof Map) {
                writer = (AmfObjectWriter) writerMap.get(Map.class);
                break;
            }
            if (value instanceof Document) {
                writer = (AmfObjectWriter) writerMap.get(Document.class);
                break;
            }

            writer = (AmfObjectWriter) writerMap.get(Class.class);
        } while (false);

        return writer;
    }
}