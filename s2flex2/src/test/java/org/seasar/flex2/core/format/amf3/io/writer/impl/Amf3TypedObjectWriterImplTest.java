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
package org.seasar.flex2.core.format.amf3.io.writer.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf3.type.factory.Amf3ReferencesFactory;
import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.AspectImpl;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.aop.interceptors.TraceInterceptor;
import org.seasar.framework.aop.proxy.AopProxy;

public class Amf3TypedObjectWriterImplTest extends S2TestCase {

    private Amf3TypedObjectWriterImpl amf3TypedObjectWriter;

    private DataOutputStream out;

    protected void setUp() throws Exception {
        include("amf3.dicon");
        out = new DataOutputStream(new ByteArrayOutputStream());
    }

    public void testWriteClassName() throws Exception {
        MethodInterceptor interceptor = new TraceInterceptor();
        Pointcut pointcut = new PointcutImpl(new String[] { "getTime" });
        Aspect aspect = new AspectImpl(interceptor, pointcut);
        AopProxy aopProxy = new AopProxy(Date.class, new Aspect[] { aspect });
        Date proxy = (Date) aopProxy.create();
        amf3TypedObjectWriter.writeClassName(proxy, out);
        Amf3ReferencesFactory referencesFactory = (Amf3ReferencesFactory) getComponent(Amf3ReferencesFactory.class);
        assertEquals("java.util.Date", referencesFactory.createReferences()
                .getStringAt(0));
    }
}