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
package org.seasar.flex2.rpc.amf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;
import org.seasar.flex2.rpc.amf.io.AmfReader;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.io.impl.AmfReaderImpl;
import org.seasar.flex2.rpc.amf.io.impl.AmfWriterImpl;

public class AmfReaderWriterTest extends TestCase {

    public void testNumber() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        AmfMessage message = new AmfMessageImpl();
        AmfBody body = new AmfBodyImpl("aaa.Hoge.foo", "response",
                new Double(1));
        message.addBody(body);
        AmfWriter writer = new AmfWriterImpl(dos, message);
        writer.write();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        DataInputStream dis = new DataInputStream(bais);
        AmfReader reader = new AmfReaderImpl(dis);
        AmfMessage message2 = reader.read();
        assertEquals("1", 1, message2.getBodySize());
        AmfBody body2 = message2.getBody(0);
        assertEquals("2", "aaa.Hoge.foo", body2.getTarget());
        assertEquals("3", "response", body2.getResponse());
        assertEquals("4", new Double(1), body2.getData());
    }

    public void testBoolean() throws Exception {
        AmfReader reader = new AmfReaderImpl(
                convertDataInputStream(Boolean.TRUE));
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

        MyBean value2 = (MyBean) convertData(value);
        assertEquals("1", 1, value2.getAaa());
        assertEquals("2", 2, value2.getBbb());
        assertEquals("3", 3, value2.getCcc(), 0);
        assertEquals("4", "4", value2.getDdd());
        assertEquals("5", true, value2.isEee());
        assertEquals("6", new Date(5), value2.getFff());
        List ggg2 = value2.getGgg();
        assertEquals("7", 2, ggg2.size());
        MyBean b4 = (MyBean) ggg2.get(0);
        MyBean b5 = (MyBean) ggg2.get(1);
        assertEquals("8", 2, b4.getAaa());
        assertEquals("9", 3, b5.getAaa());
        MyBean hhh2 = value2.getHhh();
        assertEquals("10", 4, hhh2.getAaa());
        BigDecimal iii2 = value2.getIii();
        assertEquals("11", iii, iii2);
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

    public void testCustomClass() throws Exception {
        MyBean value = new MyBean();
        value.setAaa(1);
        value.setBbb(2);
        value.setCcc(3);
        value.setDdd("4");
        value.setEee(true);
        value.setFff(new Date(5));
        List ggg = new ArrayList();
        MyBean b1 = new MyBean();
        b1.setAaa(2);
        ggg.add(b1);
        MyBean b2 = new MyBean();
        b2.setAaa(3);
        ggg.add(b2);
        value.setGgg(ggg);
        MyBean hhh = new MyBean();
        hhh.setAaa(4);
        value.setHhh(hhh);
        BigDecimal iii = new BigDecimal("1234567890123456789");
        value.setIii(iii);

        MyBean value2 = (MyBean) convertData(value);
        assertEquals("1", 1, value2.getAaa());
        assertEquals("2", 2, value2.getBbb());
        assertEquals("3", 3, value2.getCcc(), 0);
        assertEquals("4", "4", value2.getDdd());
        assertEquals("5", true, value2.isEee());
        assertEquals("6", new Date(5), value2.getFff());
        List ggg2 = value2.getGgg();
        assertEquals("7", 2, ggg2.size());
        MyBean b4 = (MyBean) ggg2.get(0);
        MyBean b5 = (MyBean) ggg2.get(1);
        assertEquals("8", 2, b4.getAaa());
        assertEquals("9", 3, b5.getAaa());
        MyBean hhh2 = value2.getHhh();
        assertEquals("10", 4, hhh2.getAaa());
        BigDecimal iii2 = value2.getIii();
        assertEquals("11", iii, iii2);
    }

    protected Object convertData(Object data) throws Exception {
        DataInputStream dis = convertDataInputStream(data);
        AmfReader reader = new AmfReaderImpl(dis);
        AmfMessage message = reader.read();
        AmfBody body = message.getBody(0);
        return body.getData();
    }

    protected DataInputStream convertDataInputStream(Object data)
            throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        AmfMessage message = new AmfMessageImpl();
        AmfBody body = new AmfBodyImpl("target", "response", data);
        message.addBody(body);
        AmfWriter writer = new AmfWriterImpl(dos, message);
        writer.write();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return new DataInputStream(bais);
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
    }
}