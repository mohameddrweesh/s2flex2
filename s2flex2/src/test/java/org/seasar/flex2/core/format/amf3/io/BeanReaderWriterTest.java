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
package org.seasar.flex2.core.format.amf3.io;

import java.util.Date;

public class BeanReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testBean() throws Exception {
        MyBean value = new MyBean();
        value.setAaa(1);
        value.setBbb(2);
        value.setCcc(3);
        value.setDdd("4");
        value.setEee(true);
        value.setFff(new Date(5));
        
        MyBean result = (MyBean)getWriteReadData(value);
        assertEquals("1", value.getAaa(), result.getAaa());
        assertEquals("2", value.getBbb(), result.getBbb());
        assertEquals("3", value.getCcc(), result.getCcc(),0);
        assertEquals("4", value.getDdd(), result.getDdd());
        assertEquals("5", value.isEee(),  result.isEee());
        assertEquals("6", value.getFff(), result.getFff());
        
    }

    public void testBeanPublicFieldOnly() throws Exception {
        MyBean2 value = new MyBean2();
        value.aaa = 1;
        value.bbb = 2;
        value.ccc = 3;
        value.ddd = "4";
        value.eee = true;
        value.fff = new Date(5);
        
        MyBean2 result = (MyBean2)getWriteReadData(value);
        assertEquals("1", value.aaa, result.aaa);
        assertEquals("2", value.bbb, result.bbb);
        assertEquals("3", value.ccc, result.ccc,0);
        assertEquals("4", value.ddd, result.ddd);
        assertEquals("5", value.eee, result.eee);
        assertEquals("6", value.fff, result.fff);
        
    }
}