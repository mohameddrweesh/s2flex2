package org.seasar.flex2.rpc.remoting.service.annotation.handler;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.remoting.service.annotation.TestRemotingService;
import org.seasar.flex2.rpc.remoting.service.annotation.TestRemotingServiceFull;
import org.seasar.flex2.rpc.remoting.service.annotation.TestServiceClass;
import org.seasar.flex2.rpc.remoting.service.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.rpc.remoting.service.annotation.handler.impl.FieldAnnotationHandler;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class FieldAnnotationHandlerTest extends S2TestCase {

    private final String PATH = "MetaDataTest.dicon";

    S2Container container;

    public FieldAnnotationHandlerTest(String name) {
        super(name);
    }

    public void testCheckAnnotation() {

        ComponentDef componentDef = container
                .getComponentDef(TestRemotingService.class);
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();

        assertNotNull("1", componentDef);
        assertTrue("2", annHandler.hasRemotingService(componentDef));

        componentDef = container.getComponentDef(TestRemotingServiceFull.class);
        annHandler = AnnotationHandlerFactory.getAnnotationHandler();

        assertNotNull("3", componentDef);
        assertTrue("4", annHandler.hasRemotingService(componentDef));

        componentDef = container.getComponentDef(TestServiceClass.class);
        annHandler = AnnotationHandlerFactory.getAnnotationHandler();

        assertNotNull("5", componentDef);
        assertFalse("6", annHandler.hasRemotingService(componentDef));

        componentDef = container.getComponentDef("TestRemotingService");
        assertTrue("7", annHandler.hasRemotingService(componentDef));

        componentDef = container.getComponentDef("TestRemotingServiceFull");
        assertTrue("8", annHandler.hasRemotingService(componentDef));

        componentDef = container.getComponentDef("TestServiceClass");
        assertFalse("9", annHandler.hasRemotingService(componentDef));
    }

    public void testCreateHandler() {
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        assertTrue("1", annHandler instanceof FieldAnnotationHandler);
    }

    protected void setUp() throws Exception {
        include(PATH);
        container = getContainer().getRoot();

    }

    protected void tearDown() throws Exception {
        super.tearDown();
        container.destroy();
    }
}
