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
package org.seasar.flex2.core.format.amf3.io.reader.factory.impl;

import java.util.Map;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf.io.reader.factory.impl.AmfDataReaderFactoryImpl;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;
import org.seasar.flex2.core.format.amf3.type.Amf3TypeDef;

public class Amf3DataReaderFactoryImpl extends AmfDataReaderFactoryImpl
        implements Amf3DataReaderFactory {

    private Map amf3DataReaderMap;

    public Amf3DataReaderFactoryImpl() {
    }

    public AmfDataReader createAmf3DataReader(final byte dataType) {
        final String key = Amf3TypeDef.toString(dataType);
        return (AmfDataReader) amf3DataReaderMap.get(key);
    }

    public AmfDataReader createDataReader(final byte dataType) {
        final String key;

        if (dataType != Amf3TypeDef.AMF3_DATA_MARKER) {
            key = AmfTypeDef.toString(dataType);
        } else {
            key = Amf3TypeDef.toString(dataType);
        }

        return (AmfDataReader) readerMap.get(key);
    }

    public void setAmf3DataReaderMap(final Map amf3DataReaderMap) {
        this.amf3DataReaderMap = amf3DataReaderMap;
    }
}