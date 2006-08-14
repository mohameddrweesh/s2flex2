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
package org.seasar.flex2.rpc.remoting.message.data.processor.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.processor.MessageBodyProcessor;
import org.seasar.flex2.rpc.remoting.message.data.processor.MessageHeaderProcessor;
import org.seasar.flex2.rpc.remoting.message.data.processor.MessageProcessor;
import org.seasar.flex2.rpc.remoting.message.io.reader.factory.MessageReaderFactory;
import org.seasar.flex2.rpc.remoting.message.io.writer.factory.MessageWriterFactory;

public class MessageProcessorImpl implements MessageProcessor {

    private static final int MESSAGE_WRITING_BUFFER_SIZE = 1024 * 2;

    private MessageBodyProcessor bodyProcessor;

    private MessageHeaderProcessor headerProcessor;

    private MessageReaderFactory readerFactory;

    private MessageWriterFactory writerFactory;

    public MessageBodyProcessor getBodyProcessor() {
        return bodyProcessor;
    }

    public MessageHeaderProcessor getHeaderProcessor() {
        return headerProcessor;
    }

    public MessageReaderFactory getReaderFactory() {
        return readerFactory;
    }

    public MessageWriterFactory getWriterFactory() {
        return writerFactory;
    }

    public void process(final DataInputStream inputStream,
            final DataOutputStream outputStream) throws IOException {
        final Message requestMessage = readMessage(inputStream);
        headerProcessor.processRequest(requestMessage);

        final Message responseMessage = bodyProcessor.process(requestMessage);
        headerProcessor.processResponse(responseMessage);

        writeMessage(responseMessage, outputStream);
    }

    public void setBodyProcessor(MessageBodyProcessor bodyProcessor) {
        this.bodyProcessor = bodyProcessor;
    }

    public void setHeaderProcessor(MessageHeaderProcessor headerProcessor) {
        this.headerProcessor = headerProcessor;
    }

    public void setReaderFactory(MessageReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public void setWriterFactory(MessageWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    private final Message readMessage(
            final DataInputStream requestDataInputStream) throws IOException {
        return readerFactory.createMessageReader(requestDataInputStream).read();
    }

    private final void writeMessage(final Message responseMessage,
            final OutputStream responseOutputStream) throws IOException {

        final ByteArrayOutputStream messageOutputStream = new ByteArrayOutputStream(
                MESSAGE_WRITING_BUFFER_SIZE);

        writerFactory.createMessageWriter(
                new DataOutputStream(messageOutputStream), responseMessage)
                .write();

        messageOutputStream.writeTo(responseOutputStream);
        responseOutputStream.flush();
    }
}