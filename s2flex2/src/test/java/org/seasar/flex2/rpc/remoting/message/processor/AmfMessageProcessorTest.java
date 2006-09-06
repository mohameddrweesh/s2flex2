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
package org.seasar.flex2.rpc.remoting.message.processor;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.remoting.message.io.reader.factory.impl.MessageReaderFactoryImpl;
import org.seasar.flex2.rpc.remoting.message.io.writer.factory.impl.MessageWriterFactoryImpl;
import org.seasar.flex2.rpc.remoting.message.processor.impl.MessageBodyProcessorImpl;
import org.seasar.flex2.rpc.remoting.message.processor.impl.MessageHeaderProcessorImpl;
import org.seasar.flex2.rpc.remoting.message.processor.impl.MessageProcessorImpl;
import org.seasar.framework.container.S2Container;

public class AmfMessageProcessorTest extends S2TestCase {

    private static String PATH = "remoting_amf0.dicon";

    public void testCreateProcessor() throws Exception {
        S2Container container = getContainer();
        Object processor = container.getComponent(MessageProcessor.class);
        assertTrue("1", processor instanceof MessageProcessorImpl);

        MessageProcessorImpl processorImpl = (MessageProcessorImpl) processor;
        assertTrue(
                "2",
                processorImpl.getBodyProcessor() instanceof MessageBodyProcessorImpl);
        assertTrue(
                "3",
                processorImpl.getHeaderProcessor() instanceof MessageHeaderProcessorImpl);
        assertTrue(
                "4",
                processorImpl.getReaderFactory() instanceof MessageReaderFactoryImpl);
        assertTrue(
                "5",
                processorImpl.getWriterFactory() instanceof MessageWriterFactoryImpl);

    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}