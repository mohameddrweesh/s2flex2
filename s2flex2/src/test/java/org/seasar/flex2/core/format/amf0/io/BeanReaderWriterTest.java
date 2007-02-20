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


public class BeanReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testBoolean() throws Exception {
        MyBean value = new MyBean();
        value.setAaa(1);
        value.setBbb(2);
        value.setCcc(3);
        value.setDdd("4");
        value.setEee(true);
        value.setFff(new Date(5));

        MyBean result = (MyBean) getWriteReadData(value);
        assertEquals("1", value.getAaa(), result.getAaa());
        assertEquals("2", value.getBbb(), result.getBbb());
        assertEquals("3", value.getCcc(), result.getCcc(), 0);
        assertEquals("4", value.getDdd(), result.getDdd());

    }
}