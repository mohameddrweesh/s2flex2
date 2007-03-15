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
import org.seasar.flex2.core.format.amf0.type.Amf0SharedObject;
import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageFactory;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageHeaderFactory;
import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;

public class AmfMessageReaderImpl implements MessageReader {

    protected AmfDataReaderFactory amfDataReaderFactory;

    protected DataInputStream inputStream;

    protected Message message;

    protected MessageBodyFactory messageBodyFactory;

    protected MessageFactory messageFactory;

    protected MessageHeaderFactory messageHeaderFactory;

    private Amf0SharedObject sharedObject;

    public void config(final DataInputStream inputStream) {
        this.inputStream = inputStream;
        this.message = createMessage();
    }

    public AmfDataReaderFactory getAmfDataReaderFactory() {
        return amfDataReaderFactory;
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
        // versionの読み込み
        readVersion();
        // Header の読み込み
        readHeader();
        // Body部のの読み込み
        readBodies();
        return message;
    }

    public void setDataReaderFactory(final AmfDataReaderFactory amfDataReaderFactory) {
        this.amfDataReaderFactory = amfDataReaderFactory;
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

    public void setSharedObject(final Amf0SharedObject sharedObject) {
        this.sharedObject = sharedObject;
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
        return amfDataReaderFactory.createDataReader(dataType).read(inputStream);
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
}