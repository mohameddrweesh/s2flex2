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
package org.seasar.flex2.rpc.amf.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfSharedObject;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.AmfObjectWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfObjectWriterFactory;

public class AmfWriterImpl implements AmfWriter {

    protected AmfMessage message;

    protected DataOutputStream outputStream;

    protected AmfSharedObject sharedObject;

    protected AmfObjectWriterFactory writerFactory;

    public void config(AmfMessage message, DataOutputStream outputStream) {
        this.message = message;
        this.outputStream = outputStream;
    }

    public AmfSharedObject getSharedObject() {
        return sharedObject;
    }

    public AmfObjectWriterFactory getWriterFactory() {
        return writerFactory;
    }

    public void setSharedObject(AmfSharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    public void setWriterFactory(AmfObjectWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void write() throws IOException {
        outputStream.writeShort(0);
        outputStream.writeShort(0);
        writeBodies();
    }

    protected void initializeSharedObject() {
        sharedObject.initialize();
    }

    protected void writeBodies() throws IOException {
        outputStream.writeShort(message.getBodySize());
        for (int i = 0; i < message.getBodySize(); ++i) {
            initializeSharedObject();
            writeBody(message.getBody(i));
        }
    }

    protected void writeBody(AmfBody body) throws IOException {
        outputStream.writeUTF(body.getTarget());
        outputStream.writeUTF(body.getResponse());
        outputStream.writeInt(-1);

        writeData(body.getData());
    }

    protected final void writeData(final Object value) throws IOException {
        AmfObjectWriter writer = writerFactory.createObjectWriter(value);
        writer.write(value, outputStream);
    }
}