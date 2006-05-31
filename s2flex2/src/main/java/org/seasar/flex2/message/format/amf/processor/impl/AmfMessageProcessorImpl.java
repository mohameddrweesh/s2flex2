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
package org.seasar.flex2.message.format.amf.processor.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import org.seasar.flex2.message.format.amf.data.AmfMessage;
import org.seasar.flex2.message.format.amf.io.reader.AmfReader;
import org.seasar.flex2.message.format.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.message.format.amf.io.writer.AmfWriter;
import org.seasar.flex2.message.format.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.flex2.message.format.amf.processor.AmfBodyProcessor;
import org.seasar.flex2.message.format.amf.processor.AmfHeaderProcessor;
import org.seasar.flex2.message.format.amf.processor.AmfMessageProcessor;

public class AmfMessageProcessorImpl implements AmfMessageProcessor {

    private AmfBodyProcessor bodyProcessor;

    private AmfHeaderProcessor headerProcessor;
    
    private AmfReaderFactory readerFactory;
    
    private AmfWriterFactory writerFactory;

    public AmfBodyProcessor getBodyProcessor() {
        return bodyProcessor;
    }

    public AmfHeaderProcessor getHeaderProcessor() {
        return headerProcessor;
    }

    public AmfReaderFactory getReaderFactory() {
        return readerFactory;
    }

    public AmfWriterFactory getWriterFactory() {
        return writerFactory;
    }

    public void process( final DataInputStream inputStream, final DataOutputStream outputStream, Map headers) throws IOException{       
        final AmfMessage requestMessage = readMessage(inputStream);
        headerProcessor.processRequest(requestMessage, headers);
        
        final AmfMessage responseMessage = bodyProcessor.process(requestMessage);
        headerProcessor.processResponse(responseMessage, headers );
        
        ByteArrayOutputStream writedOutputSteam = writeMessage( responseMessage);
        writedOutputSteam.writeTo(outputStream);
    }

    public void setBodyProcessor(AmfBodyProcessor bodyProcessor) {
        this.bodyProcessor = bodyProcessor;
    }

    public void setHeaderProcessor(AmfHeaderProcessor headerProcessor) {
        this.headerProcessor = headerProcessor;
    }

    public void setReaderFactory(AmfReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }

    public void setWriterFactory(AmfWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    private final AmfMessage readMessage( final DataInputStream requestDataInputStream) throws IOException {
        AmfReader reader = readerFactory.createReader(requestDataInputStream);
        return reader.read();
    }

    private final ByteArrayOutputStream writeMessage( final AmfMessage responseMessage) throws IOException {

        ByteArrayOutputStream amfMessageByteArray = new ByteArrayOutputStream(1024);
        DataOutputStream outputStream = new DataOutputStream(amfMessageByteArray);
        AmfWriter writer = writerFactory.createWriter( outputStream, responseMessage);
        writer.write();
        
        return amfMessageByteArray;
    }
}