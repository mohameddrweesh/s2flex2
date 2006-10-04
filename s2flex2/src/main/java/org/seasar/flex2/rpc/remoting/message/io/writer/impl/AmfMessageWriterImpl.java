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
package org.seasar.flex2.rpc.remoting.message.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.core.format.amf.type.AmfSharedObject;
import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.io.writer.MessageWriter;

public class AmfMessageWriterImpl implements MessageWriter {

    private AmfDataWriterFactory dataWriterFactory;

    private AmfSharedObject sharedObject;

    protected Message message;

    protected DataOutputStream outputStream;

    public void config(final Message message,
            final DataOutputStream outputStream) {
        this.message = message;
        this.outputStream = outputStream;
    }

    public AmfDataWriterFactory getDataWriterFactory() {
        return dataWriterFactory;
    }

    public AmfSharedObject getSharedObject() {
        return sharedObject;
    }

    public void setDataWriterFactory(final AmfDataWriterFactory writerFactory) {
        this.dataWriterFactory = writerFactory;
    }

    public void setSharedObject(final AmfSharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    public void write() throws IOException {
        outputStream.writeShort(0);
        outputStream.writeShort(0);
        writeBodies();
    }

    protected void clean() {
        sharedObject.initialize();
    }

    protected void writeBodies() throws IOException {
        outputStream.writeShort(message.getBodySize());
        for (int i = 0; i < message.getBodySize(); ++i) {
            clean();
            writeBody(message.getBody(i));
        }
    }

    protected void writeBody(final MessageBody body) throws IOException {
        outputStream.writeUTF(body.getTarget());
        outputStream.writeUTF(body.getResponse());
        outputStream.writeInt(-1);

        writeData(body.getData());
    }

    protected final void writeData(final Object value) throws IOException {
        dataWriterFactory.createDataWriter(value).write(value, outputStream);
    }
}