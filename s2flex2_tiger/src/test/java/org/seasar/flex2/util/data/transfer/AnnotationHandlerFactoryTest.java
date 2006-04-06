package org.seasar.flex2.util.data.transfer;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.util.data.transfer.annotation.AnnotationHandler;
import org.seasar.flex2.util.data.transfer.annotation.AnnotationHandlerFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.impl.BeanDescImpl;

public class AnnotationHandlerFactoryTest extends S2TestCase {

    protected void setUp() throws Exception {
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public AnnotationHandlerFactoryTest(String name) {
        super(name);
    }

    public void testCreateHandler() {
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        assertTrue("1", annHandler instanceof TigerAnnotationHandler);
    }

    public void testCreateExportStrage() {
        Object o = createTarget();
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        BeanDesc beanDesc = new BeanDescImpl(o.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            String storage = annHandler.getStorageType(beanDesc,
                    propertyDesc);
            assertEquals("2", storage, StorageType.SESSION);
        }
    }

    private Object createTarget() {
        TestClass test = new TestClass();
        return test;
    }
}