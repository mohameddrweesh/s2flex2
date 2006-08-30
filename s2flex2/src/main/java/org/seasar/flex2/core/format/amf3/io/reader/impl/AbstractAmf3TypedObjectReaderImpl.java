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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;

public abstract class AbstractAmf3TypedObjectReaderImpl extends
        AbstractAmf3ObjectReaderImpl {

    protected Amf3DataReaderFactory readerFactory;

    public void setReaderFactory(Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    protected final Object readPropertyValue(final DataInputStream inputStream)
            throws IOException {
        return readerFactory.createAmf3DataReader(inputStream.readByte()).read(inputStream);
    }
}