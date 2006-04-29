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
package org.seasar.flex2.util.transfer.annotation.handler;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.util.transfer.TestClass;
import org.seasar.flex2.util.transfer.annotation.handler.AnnotationHandler;
import org.seasar.flex2.util.transfer.annotation.handler.AnnotationHandlerFactory;
import org.seasar.flex2.util.transfer.storage.StorageType;
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
        assertTrue("1", annHandler instanceof Backport175ActionAnnotationHandler);
    }

    public void testGetExportStorageType() {
        Object o = createTarget();
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        BeanDesc beanDesc = new BeanDescImpl(o.getClass());
        PropertyDesc propertyDesc = beanDesc.getPropertyDesc("strData");
        String exportStorage = annHandler.getExportStorageType(
                propertyDesc);

        assertEquals("1", exportStorage, StorageType.SESSION);
    }

    public void testGetImportStorageType() {
        Object o = createTarget();
        AnnotationHandler annHandler = AnnotationHandlerFactory
                .getAnnotationHandler();
        BeanDesc beanDesc = new BeanDescImpl(o.getClass());
        PropertyDesc propertyDesc = beanDesc.getPropertyDesc("strData");
        String importStorage = annHandler.getImportStorageType(
                propertyDesc);

       assertEquals("1", importStorage, StorageType.SESSION);
        assertEquals("1",1,1);

    }

    private Object createTarget() {
        TestClass test = new TestClass();
        return test;
    }
}