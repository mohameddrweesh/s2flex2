package org.seasar.flex2.rpc.amf.gateway.service.annotation.handler;



import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.amf.gateway.service.annotation.TestAmfRemotingService;
import org.seasar.flex2.rpc.amf.gateway.service.annotation.TestAmfRemotingServiceFull;
import org.seasar.flex2.rpc.amf.gateway.service.annotation.TestServiceClass;
import org.seasar.flex2.rpc.amf.gateway.service.annotation.factory.AnnotationHandlerFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class Backport175AnnotationHandlerTest extends S2TestCase {
	private final String PATH = "MetaDataTest.dicon";
	S2Container container;
    protected void setUp() throws Exception {
        include(PATH);
        container = getContainer();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
       // container.destroy();
    }

    public Backport175AnnotationHandlerTest(String name) {
        super(name);
    }

    public void testCreateHandler() {
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        assertTrue("1", annHandler instanceof Backport175ActionAnnotationHandler);
    }
    
    public void testCheckAnnotation(){
    	
    	ComponentDef componentDef= container.getComponentDef(TestAmfRemotingService.class);
    	AnnotationHandler annHandler = AnnotationHandlerFactory.getAnnotationHandler();
    	
    	assertNotNull("1",componentDef);
    	assertTrue("2",annHandler.hasAmfRemotingService(componentDef));
    	
    	componentDef= container.getComponentDef(TestAmfRemotingServiceFull.class);
    	annHandler = AnnotationHandlerFactory.getAnnotationHandler();
    	
    	assertNotNull("3",componentDef);
    	assertTrue("4",annHandler.hasAmfRemotingService(componentDef));
    	
    	componentDef= container.getComponentDef(TestServiceClass.class);
    	annHandler = AnnotationHandlerFactory.getAnnotationHandler();
    	
    	assertNotNull("5",componentDef);
    	assertFalse("6",annHandler.hasAmfRemotingService(componentDef));
    	
    }
}
