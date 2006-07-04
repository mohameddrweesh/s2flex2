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
package org.seasar.flex2.rpc.remoting.message.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf.io.reader.factory.AmfDataReaderFactory;
import org.seasar.flex2.core.format.amf.type.AmfSharedObject;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageFactory;
import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;

public class AmfMessageReaderImpl implements MessageReader {

    protected DataInputStream inputStream;

    protected Message message;

    private MessageBodyFactory bodyFactory;

    private AmfDataReaderFactory dataReaderFactory;

    private MessageFactory messageFactory;

    private AmfSharedObject sharedObject;

    public AmfMessageReaderImpl() {
    }

    public void config(DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.message = createMessage();
    }

    public MessageBodyFactory getBodyFactory() {
        return bodyFactory;
    }

    public AmfDataReaderFactory getDataReaderFactory() {
        return dataReaderFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public Message read() throws IOException {
        skipHeaders();
        readBodies();
        return message;
    }

    public void setBodyFactory(MessageBodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
    }

    public void setDataReaderFactory(AmfDataReaderFactory readerFactory) {
        this.dataReaderFactory = readerFactory;
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setSharedObject(AmfSharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    protected void clean() {
        sharedObject.initialize();
    }

    protected MessageBody createBody(String target, String response, Object data) {
        return bodyFactory.createBody(target, response, data);
    }

    protected Message createMessage() {
        return messageFactory.createMessage(0);
    }

    protected void readBodies() throws IOException {
        int bodySize = inputStream.readUnsignedShort();
        for (int i = 0; i < bodySize; ++i) {
            clean();
            String target = inputStream.readUTF();
            String response = inputStream.readUTF();
            inputStream.readInt();
            Object data = readData();
            message.addBody(createBody(target, response, data));
        }
    }

    protected final Object readData() throws IOException {
        byte dataType = inputStream.readByte();

        AmfDataReader reader = dataReaderFactory.createDataReader(dataType);
        return reader.read(inputStream);
    }

    private final void skipHeaders() throws IOException {
        inputStream.readUnsignedShort();
        int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            inputStream.readUTF();
            inputStream.readByte();
            inputStream.readInt();
            readData();
        }
    }
}