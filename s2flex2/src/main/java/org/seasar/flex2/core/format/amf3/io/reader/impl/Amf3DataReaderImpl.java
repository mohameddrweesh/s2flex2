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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.io.reader.Amf0DataReader;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;

public class Amf3DataReaderImpl implements Amf0DataReader {

    protected Amf3DataReaderFactory readerFactory;

    public Object read(final DataInputStream inputStream) throws IOException {
        final byte dataType = inputStream.readByte();
        return readerFactory.createAmf3DataReader(dataType).read(inputStream);
    }

    public void setReaderFactory(final Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }
}