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
package org.seasar.flex2.core.format.amf0.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.io.reader.Amf0DataReader;
import org.seasar.flex2.core.format.amf0.io.reader.factory.Amf0DataReaderFactory;
import org.seasar.flex2.core.format.amf0.type.Amf0SharedObject;
import org.seasar.flex2.core.format.amf0.type.factory.Amf0SharedObjectFactory;

public abstract class AbstractAmf0ObjectReaderImpl implements Amf0DataReader {

    private Amf0DataReaderFactory dataReaderFactory;

    private Amf0SharedObjectFactory sharedObjectFactory;

    public Amf0DataReaderFactory getDataReaderFactory() {
        return dataReaderFactory;
    }

    public Amf0SharedObjectFactory getSharedObjectFactory() {
        return sharedObjectFactory;
    }

    public void setDataReaderFactory(
            final Amf0DataReaderFactory dataReaderFactory) {
        this.dataReaderFactory = dataReaderFactory;
    }

    public void setSharedObjectFactory(
            final Amf0SharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }

    protected final void addSharedObject(final Object value) {
        getSharedObject().addSharedObject(value);
    }

    protected final Amf0SharedObject getSharedObject() {
        return sharedObjectFactory.createSharedObject();
    }

    protected final Object readData(final byte dataType,
            final DataInputStream inputStream) throws IOException {
        return dataReaderFactory.createDataReader(dataType).read(inputStream);
    }
}