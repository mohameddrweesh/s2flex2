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
package org.seasar.flex2.rpc.remoting.message.io;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.seasar.flex2.core.format.amf3.type.ByteArray;
import org.w3c.dom.Document;

public class MyBean {

    private int aaa;

    private long bbb;

    private ByteArray byteArray;

    private double ccc;

    private String ddd;

    private Document doc;

    private boolean eee;

    private Date fff;

    private List ggg = new ArrayList();

    private MyBean hhh;

    private BigDecimal iii;

    public int getAaa() {
        return aaa;
    }

    public long getBbb() {
        return bbb;
    }

    public ByteArray getByteArray() {
        return byteArray;
    }

    public double getCcc() {
        return ccc;
    }

    public String getDdd() {
        return ddd;
    }

    public Document getDoc() {
        return doc;
    }

    public Date getFff() {
        return fff;
    }

    public List getGgg() {
        return ggg;
    }

    public MyBean getHhh() {
        return hhh;
    }

    public BigDecimal getIii() {
        return iii;
    }

    public boolean isEee() {
        return eee;
    }

    public void setAaa(int aaa) {
        this.aaa = aaa;
    }

    public void setBbb(long bbb) {
        this.bbb = bbb;
    }

    public void setByteArray(ByteArray byteArray) {
        this.byteArray = byteArray;
    }

    public void setCcc(double ccc) {
        this.ccc = ccc;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public void setEee(boolean eee) {
        this.eee = eee;
    }

    public void setFff(Date fff) {
        this.fff = fff;
    }

    public void setGgg(List ggg) {
        this.ggg = ggg;
    }

    public void setHhh(MyBean hhh) {
        this.hhh = hhh;
    }

    public void setIii(BigDecimal iii) {
        this.iii = iii;
    }
}