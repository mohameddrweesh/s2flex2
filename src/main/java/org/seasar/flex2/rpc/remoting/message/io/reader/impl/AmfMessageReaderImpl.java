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

    public void config(final DataInputStream inputStream) {
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
    /**
     * AMFデータを読み込み、Messageを返します。
     */
    public Message read() throws IOException {
        readVersion();  //versionの読み込み
        readHeader();   //Header の読み込み
        readBodies();   //Body部のの読み込み
        return message;
    }

    public void setDataReaderFactory(final AmfDataReaderFactory readerFactory) {
        this.dataReaderFactory = readerFactory;
    }

    public void setMessageBodyFactory(final MessageBodyFactory bodyFactory) {
        this.messageBodyFactory = bodyFactory;
    }

    public void setMessageFactory(final MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void setMessageHeaderFactory(
            final MessageHeaderFactory messageHeaderFactory) {
        this.messageHeaderFactory = messageHeaderFactory;
    }

    public void setSharedObject(final AmfSharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    /**
     * @deprecated
     * @throws IOException
     */
    private final void skipHeaders() throws IOException {
        inputStream.readUnsignedShort();
        final int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            inputStream.readUTF();
            inputStream.readByte();
            inputStream.readInt();
            readData();
        }
    }

    protected void readHeader() throws IOException {
        final int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            final String name = inputStream.readUTF();
            final boolean isRequired = inputStream.readBoolean();
            inputStream.readInt(); // length
            message.addHeader(messageHeaderFactory.createHeader(name,
                    readData(), isRequired));
        }
    }
    protected final void readVersion() throws IOException {
        message.setVersion(inputStream.readUnsignedShort());
    }
    
    protected void clean() {
        sharedObject.initialize();
    }

    protected MessageBody createBody(final String target,
            final String response, final Object data) {
        return messageBodyFactory.createBody(target, response, data);
    }

    protected Message createMessage() {
        return messageFactory.createRequestMessage();
    }

    protected void readBodies() throws IOException {
        final int bodySize = inputStream.readUnsignedShort();
        for (int i = 0; i < bodySize; ++i) {
            clean();
            final String target = inputStream.readUTF();
            final String response = inputStream.readUTF();
            inputStream.readInt();
            message.addBody(createBody(target, response, readData()));
        }
    }

    protected final Object readData() throws IOException {
        final byte dataType = inputStream.readByte();
        return dataReaderFactory.createDataReader(dataType).read(inputStream);
    }
}