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
package org.seasar.flex2.rpc.remoting.message.processor.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;
import org.seasar.flex2.rpc.remoting.message.io.reader.factory.MessageReaderFactory;
import org.seasar.flex2.rpc.remoting.message.io.writer.MessageWriter;
import org.seasar.flex2.rpc.remoting.message.io.writer.factory.MessageWriterFactory;
import org.seasar.flex2.rpc.remoting.message.processor.MessageBodyProcessor;
import org.seasar.flex2.rpc.remoting.message.processor.MessageHeaderProcessor;
import org.seasar.flex2.rpc.remoting.message.processor.MessageProcessor;

public class MessageProcessorImpl implements MessageProcessor {

    private static final int MESSAGE_WRITING_BUFFER_SIZE = 1024 * 4;

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
        final Message requestMessage = requestReadProcess(inputStream);

        final Message responseMessage = requestProcess(requestMessage);

        responseWriteProcess(responseMessage, outputStream);
    }

    public Message requestProcess(final Message requestMessage) {
        headerProcessor.processRequest(requestMessage);
        final Message responseMessage = bodyProcessor.process(requestMessage);
        headerProcessor.processResponse(responseMessage);
        return responseMessage;
    }

    public void setBodyProcessor(final MessageBodyProcessor bodyProcessor) {
        this.bodyProcessor = bodyProcessor;
    }

    public void setHeaderProcessor(final MessageHeaderProcessor headerProcessor) {
        this.headerProcessor = headerProcessor;
    }

    public void setReaderFactory(final MessageReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public void setWriterFactory(final MessageWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    private final Message requestReadProcess(final DataInputStream inputStream)
            throws IOException {
        final MessageReader messageReader = readerFactory
                .createMessageReader(inputStream);
        return messageReader.read();
    }

    private final void responseMessageWriteProcess(
            final Message responseMessage,
            final ByteArrayOutputStream messageByteArrayOutputStream)
            throws IOException {
        final DataOutputStream messageDataOutputStream = new DataOutputStream(
                messageByteArrayOutputStream);

        final MessageWriter messageWriter = writerFactory.createMessageWriter(
                messageDataOutputStream, responseMessage);
        messageWriter.write();
    }

    private final void responseWriteProcess(final Message responseMessage,
            final OutputStream responseOutputStream) throws IOException {

        final ByteArrayOutputStream messageByteArrayOutputStream = new ByteArrayOutputStream(
                MESSAGE_WRITING_BUFFER_SIZE);

        responseMessageWriteProcess(responseMessage,
                messageByteArrayOutputStream);

        messageByteArrayOutputStream.writeTo(responseOutputStream);
        responseOutputStream.flush();
    }
}