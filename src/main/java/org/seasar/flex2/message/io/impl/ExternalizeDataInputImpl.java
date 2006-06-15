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
package org.seasar.flex2.message.io.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.message.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.message.format.amf.io.reader.factory.Amf3DataReaderFactory;
import org.seasar.flex2.message.io.ExternalizeDataInput;

public class ExternalizeDataInputImpl implements ExternalizeDataInput {

    private DataInputStream inputStream;

    private Amf3DataReaderFactory readerFactory;

    public ExternalizeDataInputImpl() {
    }

    public boolean readBoolean() throws IOException {
        return false;
    }

    public int readByte() throws IOException {
        return 0;
    }

    public void readBytes(byte[] bytes, int offset, int length)
            throws IOException {
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public float readFloat() throws IOException {
        return 0;
    }

    public int readInt() throws IOException {
        return 0;
    }

    public String readMultiByte(int length, String charSet) throws IOException {
        return null;
    }

    public Object readObject() throws IOException {
        byte dataType = inputStream.readByte();
        AmfDataReader dataReader = readerFactory.createAmf3DataReader(dataType);
        return dataReader.read(inputStream);
    }

    public short readShort() throws IOException {
        return 0;
    }

    public int readUnsignedByte() throws IOException {
        return 0;
    }

    public int readUnsignedShort() throws IOException {
        return 0;
    }

    public String readUTF() throws IOException {
        return null;
    }

    public String readUTFBytes(int length) throws IOException {
        return null;
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setReaderFactory(Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }
}
