/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf0.io.reader.factory.impl;

import org.seasar.flex2.core.format.amf.io.AmfDataReader;
import org.seasar.flex2.core.format.amf0.io.reader.Amf0DataReader;
import org.seasar.flex2.core.format.amf0.io.reader.factory.Amf0DataReaderFactory;

public class Amf0DataReaderFactoryImpl implements Amf0DataReaderFactory {

    protected Amf0DataReader[] amf0DataReaders;

    public Amf0DataReaderFactoryImpl() {
    }

    public AmfDataReader createDataReader(final byte dataType) {
        if (isAmf0DataTypeValidation(dataType)) {
            return getAmf0DataReader(dataType);
        }
        throw new RuntimeException("Not Found Amf0Data Reader for " + dataType);
    }

    public void setAmf0DataReaders(final Amf0DataReader[] amf0DataReaders) {
        this.amf0DataReaders = amf0DataReaders;
    }

    protected final AmfDataReader getAmf0DataReader(final byte dataType) {
        return amf0DataReaders[dataType];
    }

    protected boolean isAmf0DataTypeValidation(final byte dataType) {
        return dataType < amf0DataReaders.length;
    }
}