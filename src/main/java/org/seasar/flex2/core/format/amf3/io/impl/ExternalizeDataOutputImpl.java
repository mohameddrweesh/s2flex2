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

import org.seasar.flex2.core.format.amf3.io.ExternalizeDataOutput;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.factory.Amf3DataWriterFactory;

public class ExternalizeDataOutputImpl implements ExternalizeDataOutput {

    private DataOutputStream outputStream;

    private Amf3DataWriterFactory writerFactory;

    public ExternalizeDataOutputImpl() {
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setWriterFactory(Amf3DataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void writeBoolean(boolean value) throws IOException {
    }

    public void writeByte(int value) throws IOException {
    }

    public void writeBytes(byte[] bytes, int offset, int length) throws IOException {
    }

    public void writeDouble(double value) throws IOException {
    }

    public void writeFloat(float value) throws IOException {
    }

    public void writeInt(int value) throws IOException {
    }

    public void writeMultiByte(String value, String charSet) throws IOException {
    }

    public void writeObject(Object object) throws IOException {
        final Amf3DataWriter dataWriter = writerFactory.createDataValueWriter(object);
        dataWriter.writeData(object, outputStream);
    }

    public void writeShort(int value) throws IOException {
    }

    public void writeUnsignedInt(int value) throws IOException {
    }

    public void writeUTF(String value) throws IOException {
    }

    public void writeUTFBytes(String value) throws IOException {
    }
}
