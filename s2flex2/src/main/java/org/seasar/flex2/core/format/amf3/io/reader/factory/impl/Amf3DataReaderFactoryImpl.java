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
package org.seasar.flex2.core.format.amf3.io.reader.factory.impl;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf0.io.reader.factory.impl.Amf0DataReaderFactoryImpl;
import org.seasar.flex2.core.format.amf3.io.reader.Amf3DataReader;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;

public class Amf3DataReaderFactoryImpl extends Amf0DataReaderFactoryImpl
        implements Amf3DataReaderFactory {

    protected Amf3DataReader[] amf3DataReaders;

    public AmfDataReader createAmf3DataReader(final byte dataType) {
        if (isAmf3DataTypeValidation(dataType)) {
            return getAmf3DataReader(dataType);
        }
        throw new RuntimeException("Not Found Amf3Data Reader for " + dataType);
    }

    public void setAmf3DataReaders(final Amf3DataReader[] amf3DataReaders) {
        this.amf3DataReaders = amf3DataReaders;
    }

    protected final boolean isAmf3DataTypeValidation(final byte dataType) {
        return dataType < amf3DataReaders.length;
    }

    private AmfDataReader getAmf3DataReader(final byte dataType) {
        return amf3DataReaders[dataType];
    }

}