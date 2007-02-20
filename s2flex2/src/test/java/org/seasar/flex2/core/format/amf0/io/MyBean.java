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
package org.seasar.flex2.core.format.amf0.io;

import java.util.Date;

public class MyBean {

    private int aaa;

    private long bbb;

    private double ccc;

    private String ddd;

    private boolean eee;

    private Date fff;

    private MyBean hhh;

    public int getAaa() {
        return aaa;
    }

    public long getBbb() {
        return bbb;
    }

    public double getCcc() {
        return ccc;
    }

    public String getDdd() {
        return ddd;
    }

    public Date getFff() {
        return fff;
    }

    public MyBean getHhh() {
        return hhh;
    }

    public boolean isEee() {
        return eee;
    }

    public void setAaa(final int aaa) {
        this.aaa = aaa;
    }

    public void setBbb(final long bbb) {
        this.bbb = bbb;
    }

    public void setCcc(final double ccc) {
        this.ccc = ccc;
    }

    public void setDdd(final String ddd) {
        this.ddd = ddd;
    }

    public void setEee(final boolean eee) {
        this.eee = eee;
    }

    public void setFff(final Date fff) {
        this.fff = fff;
    }

    public void setHhh(final MyBean hhh) {
        this.hhh = hhh;
    }

}
