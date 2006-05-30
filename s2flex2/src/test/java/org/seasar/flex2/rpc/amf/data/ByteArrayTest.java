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
package org.seasar.flex2.rpc.amf.data;

import java.io.IOException;
import java.util.Date;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.io.ByteArray;
import org.seasar.flex2.io.external.factory.ByteArrayFactory;
import org.seasar.flex2.io.external.impl.ByteArrayImpl;
import org.seasar.flex2.rpc.amf.data.factory.impl.ByteArrayFactoryImpl;
import org.seasar.framework.container.S2Container;

public class ByteArrayTest extends S2TestCase {

    private final static String PATH = "amf3.dicon";

    protected void setUp() throws Exception {
        include(PATH);
    }

    public void testCreateTest() {
        S2Container container = getContainer();
        Object bytearray = container.getComponent(ByteArray.class);
        assertNotNull("1", bytearray);
        assertTrue("2", bytearray instanceof ByteArrayImpl);
    }

    public void testFactoryTest() {
        S2Container container = getContainer();
        Object factory = container.getComponent(ByteArrayFactory.class);
        assertNotNull("1", factory);
        assertTrue("2", factory instanceof ByteArrayFactoryImpl);
    }

    public void testFactoryCreateTest() {
        final byte[] bs = new byte[] { 1, 2, 3, 4, 5 };
        ByteArray bytearray = createByteArrayOf(bs);
        assertNotNull("1", bytearray);
        assertTrue("2", bytearray instanceof ByteArrayImpl);

    }

    public void testReadWriteInteger() throws IOException {
        int writeInt = 9999;

        ByteArray bytearray = createByteArrayOf(null);
        bytearray.writeInt(writeInt);
        bytearray.writeUnsignedInt(0xFF);
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", 9999, bytearray.readInt());
    }
    
    public void testReadWriteString() throws IOException {
        String writeString = "あいうえお";

        ByteArray bytearray = createByteArrayOf(null);

        bytearray.writeUTF(writeString);
        bytearray.writeUTFBytes(writeString);
        bytearray.writeMultiByte(writeString, "shift-jis");
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", writeString, bytearray.readUTF());
        assertEquals("2", "あいうえお", bytearray.readUTFBytes(15));
        assertEquals("3", "あいうえ", bytearray.readMultiByte(8, "shift-jis"));
    }

    public void testReadWrite() throws IOException {
        ByteArray bytearray = createByteArrayOf(null);

        bytearray.writeBoolean(false);
        bytearray.writeBoolean(true);
        bytearray.writeInt(9999);
        bytearray.writeDouble(99999.99999);
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", false, bytearray.readBoolean());
        assertEquals("2", true, bytearray.readBoolean());
        assertEquals("3", 9999, bytearray.readInt());
        assertEquals("4", 99999.99999, bytearray.readDouble(), 0.0);
    }

    public void testReadWrite1() throws IOException {
        ByteArray bytearray = createByteArrayOf(null);

        Date nowDate = new Date();
        
        bytearray.writeBoolean(false);
        bytearray.writeBoolean(true);
        bytearray.writeInt(9999);
        bytearray.writeDouble(99999.99999);
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", false, bytearray.readBoolean());
        assertEquals("2", true, bytearray.readBoolean());
        
        bytearray.writeInt(10000);
        bytearray.writeDouble(10000.99999);
        bytearray.writeObject(nowDate);
        bytearray.flush();
        bytearray.reset();
        
        assertEquals("3", false, bytearray.readBoolean());
        assertEquals("4", true, bytearray.readBoolean());
        assertEquals("5", 10000, bytearray.readInt());
        assertEquals("6", 10000.99999, bytearray.readDouble(), 0.0);
        assertTrue("7", nowDate.equals(bytearray.readObject()));
    }
    
    public void testReadWrite2() throws IOException {
        ByteArray bytearray = createByteArrayOf(null);

        Date nowDate = new Date();
        
        bytearray.writeBoolean(false);
        bytearray.writeBoolean(true);
        bytearray.writeInt(9999);
        bytearray.writeDouble(99999.99999);
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", false, bytearray.readBoolean());
        assertEquals("2", true, bytearray.readBoolean());
        
        bytearray.writeInt(10000);
        bytearray.writeDouble(10000.99999);
        bytearray.writeObject(nowDate);
        bytearray.writeBytes(new byte[]{00,00,0x27,0x0F}, 0, 4);
        bytearray.flush();
        bytearray.reset();
        
        assertEquals("3", false, bytearray.readBoolean());
        assertEquals("4", true, bytearray.readBoolean());
        assertEquals("5", 10000, bytearray.readInt());
        assertEquals("6", 10000.99999, bytearray.readDouble(), 0.0);
        assertTrue("7", nowDate.equals(bytearray.readObject()));
        assertEquals("8", 9999, bytearray.readInt());
        
    }

    public void testCompress() throws IOException {
        ByteArray bytearray = createByteArrayOf(null);

        Date nowDate = new Date();
        
        bytearray.writeBoolean(false);
        bytearray.writeBoolean(true);
        bytearray.writeInt(9999);
        bytearray.writeDouble(99999.99999);
        bytearray.flush();
        bytearray.reset();

        assertEquals("1", false, bytearray.readBoolean());
        assertEquals("2", true, bytearray.readBoolean());
        
        bytearray.writeInt(10000);
        bytearray.writeDouble(10000.99999);
        bytearray.writeObject(nowDate);
        bytearray.writeBytes(new byte[]{00,00,0x27,0x0F}, 0, 4);
        bytearray.flush();
        bytearray.reset();

        
        bytearray.compress();
        bytearray.uncompress();
        
        assertEquals("3", false, bytearray.readBoolean());
        assertEquals("4", true, bytearray.readBoolean());
        assertEquals("5", 10000, bytearray.readInt());
        assertEquals("6", 10000.99999, bytearray.readDouble(), 0.0);
        assertTrue("7", nowDate.equals(bytearray.readObject()));
        assertEquals("8", 9999, bytearray.readInt());
        
    }
    
    private final ByteArray createByteArrayOf(final byte[] bs) {
        S2Container container = getContainer();
        ByteArrayFactory bafactory = (ByteArrayFactory) container
                .getComponent(ByteArrayFactory.class);

        ByteArray bytearray = bafactory.createByteArray(bs);
        return bytearray;
    }
}