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
package org.seasar.flex2.rpc.amf.data.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.io.charset.CharsetType;
import org.seasar.flex2.rpc.amf.data.ByteArray;
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
        initBuffer(mergaBuffers());
        outputStream.reset();
        this.pos = this.count;
    }

    public byte[] getBufferBytes() {
        byte[] buffer = new byte[count];
        System.arraycopy(buf, 0, buffer, 0, count);
        return buffer;
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
        return dataInputStream.readBoolean();
    }

    public int readByte() throws IOException {
        return dataInputStream.readByte();
    }

    public void readBytes(byte[] bytes, int offset, int length)
            throws IOException {
        dataInputStream.readFully(bytes, offset, length);
    }

    public double readDouble() throws IOException {
        return dataInputStream.readDouble();
    }

    public float readFloat() throws IOException {
        return dataInputStream.readFloat();
    }

    public int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public String readMultiByte(int length, String charSet) throws IOException {
        byte[] charBytes = new byte[length];
        read(charBytes, 0, length);
        return new String(charBytes,charSet);
    }

    public Object readObject() throws IOException {
        byte dataType = dataInputStream.readByte();
        final AmfDataReader reader = dataReaderFactory
                .createAmf3DataReader(dataType);
        return reader.read(dataInputStream);
    }

    public short readShort() throws IOException {
        return dataInputStream.readShort();
    }

    public int readUnsignedByte() throws IOException {
        return dataInputStream.readUnsignedByte();
    }

    public int readUnsignedShort() throws IOException {
        return dataInputStream.readUnsignedShort();
    }

    public String readUTF() throws IOException {
        return dataInputStream.readUTF();
    }

    public String readUTFBytes(int length) throws IOException {
        return readMultiByte(length, CharsetType.UTF8);
    }

    public void setDataReaderFactory(Amf3DataReaderFactory dataReaderFactory) {
        this.dataReaderFactory = dataReaderFactory;
    }

    public void setDataWriterFactory(Amf3DataWriterFactory dataWriterFactory) {
        this.dataWriterFactory = dataWriterFactory;
    }

    public void writeBoolean(boolean value) throws IOException {
        dataOutputStream.writeBoolean(value);
    }

    public void writeByte(int value) throws IOException {
        dataOutputStream.writeByte(value);
    }

    public void writeBytes(byte[] bytes, int offset, int length)
            throws IOException {
        dataOutputStream.write(bytes, offset, length);
    }

    public void writeDouble(double value) throws IOException {
        dataOutputStream.writeDouble(value);
    }

    public void writeFloat(float value) throws IOException {
        dataOutputStream.writeFloat(value);

    }

    public void writeInt(int value) throws IOException {
        dataOutputStream.writeInt(value);
    }

    public void writeMultiByte(String value, String charSet) throws IOException {
        byte[] charBytes = value.getBytes(charSet);
        writeBytes(charBytes, 0, charBytes.length);
    }

    public void writeObject(Object object) throws IOException {
        final Amf3DataWriter writer = dataWriterFactory
                .createDataValueWriter(object);
        writer.writeData(object, dataOutputStream);
    }

    public void writeShort(int value) throws IOException {
        dataOutputStream.writeShort(value);
    }

    public void writeUnsignedInt(int value) throws IOException {
        writeInt(value & 0x7FFF);
    }

    public void writeUTF(String value) throws IOException {
        dataOutputStream.writeUTF(value);
    }

    public void writeUTFBytes(String value) throws IOException {
        writeMultiByte(value, CharsetType.UTF8);
    }

    private final void initializeSreams() {
        outputStream = new ByteArrayOutputStream();
        dataInputStream = new DataInputStream(this);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    private byte[] mergaBuffers() {
        byte[] writeBytes = outputStream.toByteArray();
        byte[] newInitBytes = new byte[this.pos + writeBytes.length];
        System.arraycopy(this.buf, 0, newInitBytes, 0, this.pos);
        System.arraycopy(writeBytes, 0, newInitBytes, this.pos,
                writeBytes.length);
        return newInitBytes;
    }
}