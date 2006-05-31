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
package org.seasar.flex2.message.format.amf.processor;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.message.format.amf.data.factory.AmfMessageFactory;
import org.seasar.flex2.message.format.amf.data.factory.impl.Amf3BodyFactoryImpl;
import org.seasar.flex2.message.format.amf.data.factory.impl.AmfErrorFactoryImpl;
import org.seasar.flex2.message.format.amf.processor.AmfBodyProcessor;
import org.seasar.flex2.message.format.amf.processor.impl.AmfBodyProcessorImpl;
import org.seasar.framework.container.S2Container;

public class Amf3BodyProcessorTest extends S2TestCase {

    private static String PATH = "s2flex2_amf3.dicon";

    public void testCreateProcessor() throws Exception {
        S2Container container = getContainer();
        Object processor = container.getComponent(AmfBodyProcessor.class);
        assertTrue("1", processor instanceof AmfBodyProcessor);

        AmfBodyProcessorImpl processorImpl = (AmfBodyProcessorImpl) processor;
        assertTrue("2",
                processorImpl.getBodyFactory() instanceof Amf3BodyFactoryImpl);
        assertTrue("3",
                processorImpl.getErrorFactory() instanceof AmfErrorFactoryImpl);
        assertTrue("4",
                processorImpl.getMessageFactory() instanceof AmfMessageFactory);

    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}