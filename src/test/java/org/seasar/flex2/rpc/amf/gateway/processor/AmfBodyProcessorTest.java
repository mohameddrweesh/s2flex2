package org.seasar.flex2.rpc.amf.gateway.processor;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.amf.data.factory.AmfBodyFactory;
import org.seasar.flex2.rpc.amf.data.factory.AmfErrorFactory;
import org.seasar.flex2.rpc.amf.data.factory.AmfMessageFactory;
import org.seasar.flex2.rpc.amf.gateway.processor.impl.AmfBodyProcessorImpl;
import org.seasar.framework.container.S2Container;

public class AmfBodyProcessorTest extends S2TestCase {

    private static String PATH = "Amf3ProcessorTest.dicon";

    public void testCreateProcessor() throws Exception {
        S2Container container = getContainer();
        Object processor = container.getComponent(AmfBodyProcessorImpl.class);
        
        assertTrue("1", processor instanceof AmfBodyProcessorImpl);

        AmfBodyProcessorImpl processorImpl = (AmfBodyProcessorImpl) processor;
        assertTrue(
                "2",
                processorImpl.getBodyFactory() instanceof AmfBodyFactory);
        /*
        assertTrue(
                "3",
                processorImpl.getErrorFactory() instanceof AmfErrorFactory);
         */
        assertTrue(
                "4",
                processorImpl.getMessageFactory() instanceof AmfMessageFactory);
        
    }
    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}
