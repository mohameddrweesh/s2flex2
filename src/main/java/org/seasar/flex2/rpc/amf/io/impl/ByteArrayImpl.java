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
package org.seasar.flex2.rpc.amf.io.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.flex2.rpc.amf.io.ByteArray;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.flex2.rpc.amf.io.reader.data.factory.Amf3DataReaderFactory;
import org.seasar.flex2.rpc.amf.io.writer.data.Amf3DataWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.factory.Amf3DataWriterFactory;

public class ByteArrayImpl extends ByteArrayInputStream implements ByteArray {

    private static final byte[] EMPTY_BYTES = new byte[0];

    private DataInputStream dataInputStream;

    private DataOutputStream dataOutputStream;

    private Amf3DataReaderFactory dataReaderFactory;

    private Amf3DataWriterFactory dataWriterFactory;

    private ByteArrayOutputStream outputStream;

    public ByteArrayImpl() {
        super(EMPTY_BYTES);
        initializeSreams();
    }

    public void flush() {
        initBuffer(outputStream.toByteArray());
        outputStream.reset();
    }

    public void initBuffer(byte[] bytes) {
        if (bytes == null) {
            bytes = EMPTY_BYTES;
        }
        this.buf = bytes;
        this.pos = 0;
        this.count = buf.length;
    }

    public boolean readBoolean() throws IOException {
        return false;
    }

    public int readByte() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void readBytes(byte[] bytes, int offset, int length)
            throws IOException {
        // TODO Auto-generated method stub

    }

    public double readDouble() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

    public int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public String readMultiByte(int length, String charSet) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Object readObject() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public Short readShort() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public String readUTF() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public String readUTFBytes(int length) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDataReaderFactory(Amf3DataReaderFactory dataReaderFactory) {
        this.dataReaderFactory = dataReaderFactory;
    }

    public void setDataWriterFactory(Amf3DataWriterFactory dataWriterFactory) {
        this.dataWriterFactory = dataWriterFactory;
    }

    public void writeBoolean(boolean value) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeByte(int value) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeBytes(byte[] bytes, int offset, int length)
            throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeDouble(double value) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeInt(int value) throws IOException {
        dataOutputStream.writeInt(value);
    }

    public void writeMultiByte(String value, String charSet) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeObject(Object object) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeShort(int value) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeUTF(String value) throws IOException {
        // TODO Auto-generated method stub

    }

    public void writeUTFBytes(String value) throws IOException {
        // TODO Auto-generated method stub

    }

    private final void initializeSreams() {
        outputStream = new ByteArrayOutputStream();
        dataInputStream = new DataInputStream(this);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    private final AmfDataReader lookupDataReaderByType(byte type) {
        final AmfDataReader reader = dataReaderFactory
                .createAmf3DataReader(type);
        return reader;
    }
}