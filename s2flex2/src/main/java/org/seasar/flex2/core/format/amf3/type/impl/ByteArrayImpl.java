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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.factory.Amf3DataWriterFactory;
import org.seasar.flex2.core.format.amf3.type.ByteArray;
import org.seasar.flex2.core.format.amf3.type.CharsetType;
import org.seasar.flex2.core.format.amf3.type.exception.FailedCompressRuntimeException;
import org.seasar.flex2.core.format.amf3.type.exception.FailedUnCompressRuntimeException;

public class ByteArrayImpl extends ByteArrayInputStream implements ByteArray {

    private static final byte[] EMPTY_BYTES = new byte[0];

    private static final int FLATEING_BUFFER_SIZE = 1024 * 8;

    private DataInputStream dataInputStream;

    private DataOutputStream dataOutputStream;

    private Amf3DataReaderFactory dataReaderFactory;

    private Amf3DataWriterFactory dataWriterFactory;

    private ByteArrayOutputStream outputStream;

    public ByteArrayImpl() {
        super(EMPTY_BYTES);
        initializeSreams();
    }

    public void compress() {
        final Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);
        deflater.setStrategy(Deflater.DEFAULT_STRATEGY);
        deflater.setInput(buf);
        deflater.finish();

        final byte[] deflatingBuffer = new byte[FLATEING_BUFFER_SIZE];
        outputStream.reset();
        try {
            while (!deflater.needsInput()) {
                deflater.deflate(deflatingBuffer);
                outputStream.write(deflatingBuffer);
            }
        } catch (final Throwable t) {
            throw new FailedCompressRuntimeException(t);
        } finally {
            if (outputStream.size() > 0) {
                final byte[] inflatedBytes = new byte[deflater.getTotalOut()];
                System.arraycopy(outputStream.toByteArray(), 0, inflatedBytes,
                        0, inflatedBytes.length);
                initBuffer(inflatedBytes);
            }
            deflater.end();
        }
    }

    public void flush() {
        initBuffer(mergaBuffers());
        outputStream.reset();
        pos = count;
    }

    public byte[] getBufferBytes() {
        final byte[] buffer = new byte[count];
        System.arraycopy(buf, 0, buffer, 0, count);
        return buffer;
    }

    public void initBuffer(byte[] bytes) {
        if (bytes == null) {
            bytes = EMPTY_BYTES;
        }
        buf = bytes;
        pos = 0;
        count = buf.length;
    }

    public boolean readBoolean() throws IOException {
        return dataInputStream.readBoolean();
    }

    public byte readByte() throws IOException {
        return dataInputStream.readByte();
    }

    public void readBytes(final byte[] bytes, final int offset, final int length)
            throws IOException {
        dataInputStream.readFully(bytes, offset, length);
    }

    public char readChar() throws IOException {
        return dataInputStream.readChar();
    }

    public double readDouble() throws IOException {
        return dataInputStream.readDouble();
    }

    public float readFloat() throws IOException {
        return dataInputStream.readFloat();
    }

    public void readFully(final byte[] b) throws IOException {
        dataInputStream.readFully(b);
    }

    public void readFully(final byte[] b, final int off, final int len)
            throws IOException {
        dataInputStream.readFully(b, off, len);
    }

    public int readInt() throws IOException {
        return dataInputStream.readInt();
    }

    public String readLine() throws IOException {
        return null;
    }

    public long readLong() throws IOException {
        return dataInputStream.readLong();
    }

    public String readMultiByte(final int length, final String charSet)
            throws IOException {
        final byte[] charBytes = new byte[length];
        read(charBytes, 0, length);
        return new String(charBytes, charSet);
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        final byte dataType = dataInputStream.readByte();
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

    public String readUTFBytes(final int length) throws IOException {
        return readMultiByte(length, CharsetType.UTF8);
    }

    public void setDataReaderFactory(
            final Amf3DataReaderFactory dataReaderFactory) {
        this.dataReaderFactory = dataReaderFactory;
    }

    public void setDataWriterFactory(
            final Amf3DataWriterFactory dataWriterFactory) {
        this.dataWriterFactory = dataWriterFactory;
    }

    public int skipBytes(final int n) throws IOException {
        return dataInputStream.skipBytes(n);
    }

    public void uncompress() {
        final Inflater inflater = new Inflater(false);
        inflater.setInput(buf);
        final byte[] inflatingBuffer = new byte[FLATEING_BUFFER_SIZE];
        outputStream.reset();
        try {
            while (!inflater.needsInput()) {
                inflater.inflate(inflatingBuffer);
                outputStream.write(inflatingBuffer);
            }
        } catch (final Throwable t) {
            throw new FailedUnCompressRuntimeException(t);
        } finally {
            if (outputStream.size() > 0) {
                final byte[] inflatedBytes = new byte[inflater.getTotalOut()];
                System.arraycopy(outputStream.toByteArray(), 0, inflatedBytes,
                        0, inflatedBytes.length);
                initBuffer(inflatedBytes);
            }
            inflater.end();
        }
    }

    public void write(final byte[] b) throws IOException {
        dataOutputStream.write(b);
    }

    public void write(final byte[] b, final int off, final int len)
            throws IOException {
        dataOutputStream.write(b, off, len);
    }

    public void write(final int b) throws IOException {
        dataOutputStream.write(b);
    }

    public void writeBoolean(final boolean value) throws IOException {
        dataOutputStream.writeBoolean(value);
    }

    public void writeByte(final int value) throws IOException {
        dataOutputStream.writeByte(value);
    }

    public void writeBytes(final byte[] bytes, final int offset,
            final int length) throws IOException {
        dataOutputStream.write(bytes, offset, length);
    }

    public void writeBytes(final String s) throws IOException {
        dataOutputStream.writeBytes(s);
    }

    public void writeChar(final int v) throws IOException {
        dataOutputStream.writeChar(v);
    }

    public void writeChars(final String s) throws IOException {
        dataOutputStream.writeChars(s);
    }

    public void writeDouble(final double value) throws IOException {
        dataOutputStream.writeDouble(value);
    }

    public void writeFloat(final float value) throws IOException {
        dataOutputStream.writeFloat(value);

    }

    public void writeInt(final int value) throws IOException {
        dataOutputStream.writeInt(value);
    }

    public void writeLong(final long v) throws IOException {
        dataOutputStream.writeLong(v);
    }

    public void writeMultiByte(final String value, final String charSet)
            throws IOException {
        final byte[] charBytes = value.getBytes(charSet);
        writeBytes(charBytes, 0, charBytes.length);
    }

    public void writeObject(final Object object) throws IOException {
        final Amf3DataWriter writer = dataWriterFactory
                .createAmf3DataWriter(object);
        writer.writeData(object, dataOutputStream);
    }

    public void writeShort(final int value) throws IOException {
        dataOutputStream.writeShort(value);
    }

    public void writeUnsignedInt(final int value) throws IOException {
        writeInt(value & 0x7FFF);
    }

    public void writeUTF(final String value) throws IOException {
        dataOutputStream.writeUTF(value);
    }

    public void writeUTFBytes(final String value) throws IOException {
        writeMultiByte(value, CharsetType.UTF8);
    }

    private final void initializeSreams() {
        outputStream = new ByteArrayOutputStream();
        dataInputStream = new DataInputStream(this);
        dataOutputStream = new DataOutputStream(outputStream);
    }

    private final byte[] mergaBuffers() {
        final byte[] writeBytes = outputStream.toByteArray();
        final byte[] newInitBytes = new byte[pos + writeBytes.length];
        System.arraycopy(buf, 0, newInitBytes, 0, pos);
        System.arraycopy(writeBytes, 0, newInitBytes, pos, writeBytes.length);
        return newInitBytes;
    }
}