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
package org.seasar.flex2.core.format.amf3.io.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.io.ExternalObjectOutput;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.factory.Amf3DataWriterFactory;

public class ExternalObjectOutputImpl implements ExternalObjectOutput {

    private DataOutputStream outputStream;

    private Amf3DataWriterFactory writerFactory;

    public ExternalObjectOutputImpl() {
    }

    public void close() throws IOException {
        outputStream.close();
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setWriterFactory(Amf3DataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void write(byte[] b) throws IOException {
        outputStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        outputStream.write(b, off, len);

    }

    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    public void writeBoolean(boolean v) throws IOException {
        outputStream.writeBoolean(v);
    }

    public void writeByte(int v) throws IOException {
        outputStream.writeByte(v);
    }

    public void writeBytes(String s) throws IOException {
        outputStream.writeBytes(s);
    }

    public void writeChar(int v) throws IOException {
        outputStream.writeChar(v);
    }

    public void writeChars(String s) throws IOException {
        outputStream.writeChars(s);
    }

    public void writeDouble(double v) throws IOException {
        outputStream.writeDouble(v);
    }

    public void writeFloat(float v) throws IOException {
        outputStream.writeFloat(v);
    }

    public void writeInt(int v) throws IOException {
        outputStream.writeInt(v);
    }

    public void writeLong(long v) throws IOException {
        outputStream.writeLong(v);
    }

    public void writeObject(Object object) throws IOException {
        final Amf3DataWriter dataWriter = writerFactory
                .createDataValueWriter(object);
        dataWriter.writeData(object, outputStream);
    }

    public void writeShort(int v) throws IOException {
        outputStream.writeShort(v);
    }

    public void writeUTF(String str) throws IOException {
        outputStream.writeUTF(str);
    }
}
