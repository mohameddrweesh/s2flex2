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
package org.seasar.flex2.rpc.amf.io;

import java.io.IOException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.rpc.amf.io.factory.ByteArrayFactory;
import org.seasar.flex2.rpc.amf.io.factory.impl.ByteArrayFactoryImpl;
import org.seasar.flex2.rpc.amf.io.impl.ByteArrayImpl;
import org.seasar.framework.container.S2Container;

import org.seasar.flex2.rpc.amf.io.ByteArray;

import sun.security.krb5.internal.ccache.ar;


public class ByteArrayTest extends S2TestCase {

    private final static String PATH = "amf3.dicon";
    
    protected void setUp() throws Exception {
        include(PATH);
    }
    
    public void testCreateTest(){
        S2Container container = getContainer();
        Object bytearray = container.getComponent(ByteArray.class);
        assertNotNull("1", bytearray);
        assertTrue("2", bytearray instanceof ByteArrayImpl);
    }

    public void testFactoryTest(){
        S2Container container = getContainer();
        Object factory = container.getComponent(ByteArrayFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof ByteArrayFactoryImpl);
    }
    
    public void testFactoryCreateTest(){
        final byte[] bs = new byte[]{1,2,3,4,5};
        ByteArray bytearray = createByteArrayOf(bs);
        assertNotNull("1", bytearray);
        assertTrue("2", bytearray instanceof ByteArrayImpl);
        
    }
    
    public void testReadWriteInteger() throws IOException{
        int writeInt = 9999;
        
        ByteArray bytearray = createByteArrayOf(null);
        
        bytearray.writeInt(writeInt);
        bytearray.flush();
        
        int readInt = bytearray.readInt();
        
        assertEquals("1",writeInt, readInt);
    }

    private final ByteArray createByteArrayOf(final byte[] bs) {
        S2Container container = getContainer();
        ByteArrayFactory bafactory = (ByteArrayFactory)container.getComponent(ByteArrayFactory.class);
        
        ByteArray bytearray = bafactory.createByteArray(bs);
        return bytearray;
    }
}