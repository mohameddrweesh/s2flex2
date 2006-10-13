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
package org.seasar.flex2.rpc.remoting.service.adjustor;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.seasar.extension.unit.S2TestCase;

public class MapArgumentAdjustorTest extends S2TestCase {

    private static String PATH = "MapArgumentAdjustorTest.dicon";

    private ArgumentAdjustor argumentAdjustor;

    public void testIsTarget() throws Exception {
        assertNotNull(argumentAdjustor);
        Map map = new HashMap();
        assertFalse("1", argumentAdjustor.isTarget(HashMap.class, map));
        assertTrue("2", argumentAdjustor.isTarget(TestBean.class, map));
    }

    public void testAdjust() throws Exception {
        Map map = new HashMap();
        map.put("name", "test name");
        map.put("age", new Integer(11));
        map.put("flag", Boolean.TRUE);
        map.put("birthday", Calendar.getInstance().getTime());

        Object o = argumentAdjustor.adjust(TestBean.class, map);
        assertTrue("1", o instanceof TestBean);
        
        TestBean t = (TestBean)o;
        
        assertEquals("2",t.getName(),(String)map.get("name"));
        assertEquals("3",t.getAge(),11);
        assertEquals("4",t.isFlag(),true);
        assertEquals("5",t.getBirthday(),(Date)map.get("birthday"));
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}
