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
package org.seasar.flex2.rpc.amf.io.external.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.io.external.ExternalizeDataInput;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.flex2.rpc.amf.io.reader.data.factory.Amf3DataReaderFactory;

public class ExternalizeDataInputImpl implements ExternalizeDataInput {

    private DataInputStream inputStream;

    private Amf3DataReaderFactory readerFactory;

    public ExternalizeDataInputImpl() {
    }

    public Object readObject() throws IOException {
        byte dataType = inputStream.readByte();
        AmfDataReader dataReader = readerFactory.createAmf3DataReader(dataType);
        return dataReader.read(inputStream);
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setReaderFactory(Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }
}
