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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.io.charset.CharsetType;
import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.ByteArray;
import org.seasar.flex2.rpc.amf.data.factory.AmfBodyFactory;
import org.seasar.flex2.rpc.amf.data.factory.AmfMessageFactory;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;
import org.seasar.flex2.rpc.amf.io.factory.ByteArrayFactory;
import org.seasar.flex2.rpc.amf.io.reader.AmfReader;
import org.seasar.flex2.rpc.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.util.Amf3DataUtil;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.DocumentBuilderFactoryUtil;
import org.seasar.framework.util.DocumentBuilderUtil;
import org.seasar.framework.util.ResourceUtil;
import org.w3c.dom.Document;

public class Amf3ReaderWriterTest extends S2TestCase {

    private final static String PATH = "amf3.dicon";

    protected void setUp() throws Exception {
        include(PATH);
    }

    public AmfMessageFactory amfMessageFactory;

    public AmfBodyFactory amfBodyFactory;

    public AmfWriterFactory amfWriterFactory;

    public AmfReaderFactory amfReaderFactory;

    public void testNumber() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        AmfMessage message = new AmfMessageImpl();
        AmfBody body = new AmfBodyImpl("aaa.Hoge.foo", "response",
                new Double(1));
        message.addBody(body);
        AmfWriter writer = amfWriterFactory.createWriter(dos, message);
        writer.write();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        AmfReader reader = amfReaderFactory.createReader(dis);
        AmfMessage message2 = reader.read();
        assertEquals("1", 1, message2.getBodySize());
        AmfBody body2 = message2.getBody(0);
        assertEquals("2", "aaa.Hoge.foo", body2.getTarget());
        assertEquals("3", "response", body2.getResponse());
        assertEquals("4", new Double(1), body2.getData());
    }

    public void testBoolean() throws Exception {
        AmfReader reader = amfReaderFactory
                .createReader(convertDataInputStream(Boolean.TRUE));
        AmfMessage message2 = reader.read();
        AmfBody body2 = message2.getBody(0);
        assertEquals("1", Boolean.TRUE, body2.getData());
    }

    public void testString() throws Exception {
        assertEquals("1", "abc", convertData("abc"));
    }

    public void testObject() throws Exception {
        Map value = new HashMap();
        value.put("aaa", "111");
        value.put("bbb", "222");
        Map value2 = (Map) convertData(value);
        assertEquals("1", 2, value2.size());
        assertEquals("2", "111", value2.get("aaa"));
        assertEquals("3", "222", value2.get("bbb"));
    }

    public void testObjectForRemoteClass() throws Exception {
        Map value = new HashMap();
        value.put("_remoteClass", MyBean.class.getName());
        value.put("Aaa", new Double(1));
        value.put("Bbb", new Double(2));
        value.put("Ccc", new Double(3));
        value.put("Ddd", "4");
        value.put("Eee", Boolean.TRUE);
        value.put("Fff", new Date(5));
        List ggg = new ArrayList();
        Map b1 = new HashMap();
        b1.put("_remoteClass", MyBean.class.getName());
        b1.put("Aaa", new Double(2));
        ggg.add(b1);
        Map b2 = new HashMap();
        b2.put("_remoteClass", MyBean.class.getName());
        b2.put("Aaa", new Double(3));
        ggg.add(b2);
        value.put("Ggg", ggg);
        Map hhh = new HashMap();
        hhh.put("_remoteClass", MyBean.class.getName());
        hhh.put("Aaa", new Double(4));
        value.put("Hhh", hhh);
        BigDecimal iii = new BigDecimal("1234567890123456789");
        value.put("Iii", iii);

        Map value2 = (Map) convertData(value);
        assertEquals("1", new Double(1), value2.get("Aaa"));
        assertEquals("2", new Double(2), value2.get("Bbb"));
        assertEquals("3", new Double(3), value2.get("Ccc"));
        assertEquals("4", "4", value2.get("Ddd"));
        assertEquals("5", Boolean.TRUE, value2.get("Eee"));
        assertEquals("6", new Date(5), value2.get("Fff"));
        List ggg2 = (List) value2.get("Ggg");
        assertEquals("7", 2, ggg2.size());
        Map b4 = (Map) ggg2.get(0);
        Map b5 = (Map) ggg2.get(1);
        assertEquals("8", new Double(2), b4.get("Aaa"));
        assertEquals("9", new Double(3), b5.get("Aaa"));
        Map hhh2 = (Map) value2.get("Hhh");
        assertEquals("10", new Double(4), hhh2.get("Aaa"));
        String iii2 = (String) value2.get("Iii");
        assertEquals("11", iii, new BigDecimal(iii2));
    }

    public void testMapElementNumberLimitTest() throws Exception {
        Map value = new HashMap();
        for (int i = 0; i < 100; i++) {
            value.put("Ccc" + i, new Double(i));
        }

        Map value2 = (Map) convertData(value);
        for (int i = 0; i < 100; i++) {
            assertEquals("3", new Double(i), value2.get("Ccc" + i));
        }
    }

    public void testListElementNumberLimitTest() throws Exception {
        List value = new ArrayList();

        for (int i = 0; i < 100; i++) {
            value.add("" + i);
        }

        List value2 = (List) convertData(value);
        assertEquals("1", 100, value2.size());

        for (int i = 0; i < 100; i++) {
            assertEquals("2", "" + i, value2.get(i));
        }
    }

    public void testNull() throws Exception {
        assertEquals("1", null, convertData(null));
    }

    public void testArrayForArray() throws Exception {
        Object[] value = new Object[] { "111", "222" };
        List value2 = (List) convertData(value);
        assertEquals("1", 2, value2.size());
        assertEquals("2", "111", value2.get(0));
        assertEquals("3", "222", value2.get(1));
    }

    public void testArrayForArrayList() throws Exception {
        List value = new ArrayList();
        value.add("111");
        value.add("222");
        List value2 = (List) convertData(value);
        assertEquals("1", 2, value2.size());
        assertEquals("2", "111", value2.get(0));
        assertEquals("3", "222", value2.get(1));
    }

    public void testArrayForIterator() throws Exception {
        List value = new ArrayList();
        value.add("111");
        value.add("222");
        List value2 = (List) convertData(value.iterator());
        assertEquals("1", 2, value2.size());
        assertEquals("2", "111", value2.get(0));
        assertEquals("3", "222", value2.get(1));
    }

    public void testXml() throws Exception {
        Document xml1 = createXmlDocument();

        Document xml2 = (Document) convertData(xml1);

        assertEquals("1", Amf3DataUtil.toXmlString(xml1), Amf3DataUtil
                .toXmlString(xml2));

    }

    public void testByteArray() throws Exception {
        final byte[] bs = new byte[] { 1, 2, 3, 4, 5 };
        ByteArray bytearray = createByteArrayOf(bs);

        ByteArray bytearray1 = (ByteArray) convertData(bytearray);

        assertTrue("1", Arrays.equals(bs, bytearray1.getBufferBytes()));
    }

    public void testCustomClass() throws Exception {
        MyBean value = new MyBean();
        value.setAaa(-1);
        value.setBbb(0xFFFFFFF);
        value.setCcc(-0xFFFFFFF);
        value.setDdd("4");
        value.setEee(true);
        value.setFff(new Date(5));
        List ggg = new ArrayList();
        MyBean b1 = new MyBean();
        b1.setAaa(0x200000);
        ggg.add(b1);
        MyBean b2 = new MyBean();
        b2.setAaa(-0x200000);
        ggg.add(b2);
        value.setGgg(ggg);
        MyBean hhh = new MyBean();
        hhh.setAaa(4);
        value.setHhh(hhh);
        BigDecimal iii = new BigDecimal("1234567890123456789");
        value.setIii(iii);

        Document xml1 = createXmlDocument();
        value.setDoc(xml1);

        ByteArray bytearray = createByteArrayOf(null);
        bytearray.writeBoolean(false);
        bytearray.writeBoolean(true);
        bytearray.writeInt(10902);
        bytearray.writeUTF("あいうえお");
        byte[] bytes1 = "バイト列".getBytes(CharsetType.UTF8);
        bytearray.writeBytes(bytes1, 0, bytes1.length);
        bytearray.writeObject(value.getFff());
        bytearray.flush();
        value.setByteArray(bytearray);

        MyBean value2 = (MyBean) convertData(value);
        assertEquals("1", -1, value2.getAaa());
        assertEquals("2", 0xFFFFFFF, value2.getBbb());
        assertEquals("3", -0xFFFFFFF, value2.getCcc(), 0);
        assertEquals("4", "4", value2.getDdd());
        assertEquals("5", true, value2.isEee());
        assertEquals("6", new Date(5), value2.getFff());
        List ggg2 = value2.getGgg();
        assertEquals("7", 2, ggg2.size());
        MyBean b4 = (MyBean) ggg2.get(0);
        MyBean b5 = (MyBean) ggg2.get(1);
        assertEquals("8", 0x200000, b4.getAaa());
        assertEquals("9", -0x200000, b5.getAaa());
        MyBean hhh2 = value2.getHhh();
        assertEquals("10", 4, hhh2.getAaa());
        BigDecimal iii2 = value2.getIii();
        assertEquals("11", iii, iii2);

        assertNull("12", value2.getHhh().getDdd());

        Document xml2 = value2.getDoc();
        assertEquals("13", Amf3DataUtil.toXmlString(xml1), Amf3DataUtil
                .toXmlString(xml2));

        ByteArray bytearray1 = value2.getByteArray();
        assertEquals("14", false, bytearray1.readBoolean());
        assertEquals("15", true, bytearray1.readBoolean());
        assertEquals("16", 10902, bytearray1.readInt());
        assertEquals("17", "あいうえお", bytearray1.readUTF());

        byte[] bytes2 = new byte[bytes1.length];
        bytearray1.readBytes(bytes2, 0, bytes2.length);
        assertTrue("18", Arrays.equals(bytes1, bytes2));

        assertEquals("19", value.getFff(), bytearray1.readObject());
    }

    public void testExterbalizableObject() throws Exception {
        MyBean value = new MyBean();
        value.setAaa(-1);
        value.setBbb(0xFFFFFFF);
        value.setCcc(-0xFFFFFFF);
        value.setDdd("4");
        value.setEee(true);
        value.setFff(new Date(5));
        List ggg = new ArrayList();
        MyBean b1 = new MyBean();
        b1.setAaa(0x200000);
        ggg.add(b1);
        MyBean b2 = new MyBean();
        b2.setAaa(-0x200000);
        ggg.add(b2);
        value.setGgg(ggg);
        MyBean hhh = new MyBean();
        hhh.setAaa(4);
        value.setHhh(hhh);
        BigDecimal iii = new BigDecimal("1234567890123456789");
        value.setIii(iii);

        TestExternalizeObject externalizableObject = new TestExternalizeObject();
        externalizableObject.setMyBean(value);

        TestExternalizeObject externalizeObject2 = (TestExternalizeObject) convertData(externalizableObject);

        MyBean value2 = externalizableObject.getMyBean();
        assertEquals("1", -1, value2.getAaa());
        assertEquals("2", 0xFFFFFFF, value2.getBbb());
        assertEquals("3", -0xFFFFFFF, value2.getCcc(), 0);
        assertEquals("4", "4", value2.getDdd());
        assertEquals("5", true, value2.isEee());
        assertEquals("6", new Date(5), value2.getFff());
        List ggg2 = value2.getGgg();
        assertEquals("7", 2, ggg2.size());
        MyBean b4 = (MyBean) ggg2.get(0);
        MyBean b5 = (MyBean) ggg2.get(1);
        assertEquals("8", 0x200000, b4.getAaa());
        assertEquals("9", -0x200000, b5.getAaa());
        MyBean hhh2 = value2.getHhh();
        assertEquals("10", 4, hhh2.getAaa());
        BigDecimal iii2 = value2.getIii();
        assertEquals("11", iii, iii2);

        assertNull("12", value2.getHhh().getDdd());

    }

    protected Object convertData(Object data) throws Exception {
        DataInputStream dis = convertDataInputStream(data);
        AmfReader reader = amfReaderFactory.createReader(dis);
        AmfMessage message = reader.read();
        AmfBody body = message.getBody(0);
        return body.getData();
    }

    protected DataInputStream convertDataInputStream(Object data)
            throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        AmfMessage message = amfMessageFactory.createMessage(3);
        AmfBody body = amfBodyFactory.createBody("target", "response", data);
        message.addBody(body);
        AmfWriter writer = amfWriterFactory.createWriter(dos, message);
        writer.write();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return new DataInputStream(bais);
    }

    private final Document createXmlDocument() throws FileNotFoundException {
        URL url = ResourceUtil
                .getResource("org/seasar/flex2/rpc/amf/io/testXml.xml");
        File testXml = new File(url.getPath());
        DocumentBuilder builder = DocumentBuilderFactoryUtil
                .newDocumentBuilder();
        Document xml1 = DocumentBuilderUtil.parse(builder,
                new BufferedInputStream(new FileInputStream(testXml)));
        return xml1;
    }

    private final ByteArray createByteArrayOf(final byte[] bs) {
        S2Container container = getContainer();
        ByteArrayFactory bafactory = (ByteArrayFactory) container
                .getComponent(ByteArrayFactory.class);

        ByteArray bytearray = bafactory.createByteArray(bs);
        return bytearray;
    }

    public static class MyBean {

        private int aaa;

        private long bbb;

        private double ccc;

        private String ddd;

        private boolean eee;

        private Date fff;

        private List ggg = new ArrayList();

        private MyBean hhh;

        private BigDecimal iii;

        private Document doc;

        private ByteArray byteArray;

        public int getAaa() {
            return aaa;
        }

        public void setAaa(int aaa) {
            this.aaa = aaa;
        }

        public long getBbb() {
            return bbb;
        }

        public void setBbb(long bbb) {
            this.bbb = bbb;
        }

        public double getCcc() {
            return ccc;
        }

        public void setCcc(double ccc) {
            this.ccc = ccc;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        public boolean isEee() {
            return eee;
        }

        public void setEee(boolean eee) {
            this.eee = eee;
        }

        public Date getFff() {
            return fff;
        }

        public void setFff(Date fff) {
            this.fff = fff;
        }

        public List getGgg() {
            return ggg;
        }

        public void setGgg(List ggg) {
            this.ggg = ggg;
        }

        public MyBean getHhh() {
            return hhh;
        }

        public void setHhh(MyBean hhh) {
            this.hhh = hhh;
        }

        public BigDecimal getIii() {
            return iii;
        }

        public void setIii(BigDecimal iii) {
            this.iii = iii;
        }

        public ByteArray getByteArray() {
            return byteArray;
        }

        public void setByteArray(ByteArray byteArray) {
            this.byteArray = byteArray;
        }

        public Document getDoc() {
            return doc;
        }

        public void setDoc(Document doc) {
            this.doc = doc;
        }
    }
}