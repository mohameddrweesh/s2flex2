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
package org.seasar.flex2.core.format.amf3.type.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;
import org.seasar.flex2.core.format.amf3.type.ExternalObjectInput;

public class ExternalObjectInputImpl implements ExternalObjectInput {

    private DataInputStream inputStream;

    private Amf3DataReaderFactory readerFactory;

    public int available() throws IOException {
        return inputStream.available();
    }

    public void close() throws IOException {
        inputStream.close();
    }

    public int read() throws IOException {
        return inputStream.read();
    }

    public int read(final byte[] b) throws IOException {
        return inputStream.read(b);
    }

    public int read(final byte[] b, final int off, final int len)
            throws IOException {
        return inputStream.read(b, off, len);
    }

    public boolean readBoolean() throws IOException {
        return inputStream.readBoolean();
    }

    public byte readByte() throws IOException {
        return inputStream.readByte();
    }

    public char readChar() throws IOException {
        return inputStream.readChar();
    }

    public double readDouble() throws IOException {
        return inputStream.readDouble();
    }

    public float readFloat() throws IOException {
        return inputStream.readFloat();
    }

    public void readFully(final byte[] b) throws IOException {
        inputStream.readFully(b);
    }

    public void readFully(final byte[] b, final int off, final int len)
            throws IOException {
        inputStream.readFully(b, off, len);
    }

    public int readInt() throws IOException {
        return inputStream.readInt();
    }

    public String readLine() throws IOException {
        return null;
    }

    public long readLong() throws IOException {
        return inputStream.readLong();
    }

    public Object readObject() throws IOException {
        final byte dataType = inputStream.readByte();
        final AmfDataReader dataReader = readerFactory
                .createAmf3DataReader(dataType);
        return dataReader.read(inputStream);
    }

    public short readShort() throws IOException {
        return inputStream.readShort();
    }

    public int readUnsignedByte() throws IOException {
        return inputStream.readUnsignedByte();
    }

    public int readUnsignedShort() throws IOException {
        return inputStream.readUnsignedShort();
    }

    public String readUTF() throws IOException {
        return inputStream.readUTF();
    }

    public void setInputStream(final DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setReaderFactory(final Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public long skip(final long n) throws IOException {
        return inputStream.skip(n);
    }

    public int skipBytes(final int n) throws IOException {
        return inputStream.skipBytes(n);
    }

    public void write(final byte[] b) throws IOException {
    }

    public void write(final int b) throws IOException {
    }
}
