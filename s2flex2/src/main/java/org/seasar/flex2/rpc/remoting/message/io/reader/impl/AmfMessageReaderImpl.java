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

import org.seasar.flex2.core.format.amf.io.reader.factory.AmfDataReaderFactory;
import org.seasar.flex2.core.format.amf.type.AmfSharedObject;
import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageHeaderFactory;
import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;

public class AmfMessageReaderImpl implements MessageReader {

    private AmfSharedObject sharedObject;

    protected AmfDataReaderFactory dataReaderFactory;

    protected DataInputStream inputStream;
    
    protected Message message;

    protected MessageBodyFactory messageBodyFactory;

    protected MessageFactory messageFactory;

    protected MessageHeaderFactory messageHeaderFactory;

    public AmfMessageReaderImpl() {
    }

    public void config(DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.message = createMessage();
    }

    public AmfDataReaderFactory getDataReaderFactory() {
        return dataReaderFactory;
    }

    public MessageBodyFactory getMessageBodyFactory() {
        return messageBodyFactory;
    }

    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    public MessageHeaderFactory getMessageHeaderFactory() {
        return messageHeaderFactory;
    }

    public Message read() throws IOException {
        skipHeaders();
        readBodies();
        return message;
    }

    public void setDataReaderFactory(AmfDataReaderFactory readerFactory) {
        this.dataReaderFactory = readerFactory;
    }

    public void setMessageBodyFactory(MessageBodyFactory bodyFactory) {
        this.messageBodyFactory = bodyFactory;
    }

    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setMessageHeaderFactory(MessageHeaderFactory messageHeaderFactory) {
        this.messageHeaderFactory = messageHeaderFactory;
    }

    public void setSharedObject(AmfSharedObject sharedObject) {
        this.sharedObject = sharedObject;
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

    protected void clean() {
        sharedObject.initialize();
    }

    protected MessageBody createBody(String target, String response, Object data) {
        return messageBodyFactory.createBody(target, response, data);
    }

    protected Message createMessage() {
        return messageFactory.createRequestMessage();
    }

    protected void readBodies() throws IOException {
        int bodySize = inputStream.readUnsignedShort();
        for (int i = 0; i < bodySize; ++i) {
            clean();
            String target = inputStream.readUTF();
            String response = inputStream.readUTF();
            inputStream.readInt();
            message.addBody(createBody(target, response, readData()));
        }
    }

    protected final Object readData() throws IOException {
        byte dataType = inputStream.readByte();
        return dataReaderFactory.createDataReader(dataType).read(inputStream);
    }
}