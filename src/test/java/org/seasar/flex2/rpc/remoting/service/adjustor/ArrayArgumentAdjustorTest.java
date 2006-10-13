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
import org.seasar.flex2.core.format.amf3.type.Amf3Object;

public class ArrayArgumentAdjustorTest extends S2TestCase {

    private static String PATH = "ArrayArgumentAdjustorTest.dicon";

    private ArgumentAdjustor argumentAdjustor;

    public void testIsTarget() throws Exception {
        assertNotNull(argumentAdjustor);
        Map map = new HashMap();
        TestBean[] ts = new TestBean[]{};
        assertFalse("1", argumentAdjustor.isTarget(TestBean[].class, map));
        assertTrue("2", argumentAdjustor.isTarget(TestBean[].class, ts));
    }
    
    public void testAdjustCustomArray() throws Exception {
        Object[] strs = new Object[5];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = "name" + i;
        }

        Object o = argumentAdjustor.adjust(String[].class, strs);
        assertTrue("1", o instanceof String[]);

        String[] strs2 = (String[]) o;

        for (int i = 0; i < strs.length; i++) {
             assertEquals("2", strs2[i], strs[i]);
        }
    }

    public void testAdjustCustomArray2() throws Exception {
        Object[] objs = new Object[5];
        for (int i = 0; i < objs.length; i++) {
            TestBean tb = new TestBean();
            tb.setName("name"+i);
            objs[i] = tb;
        }

        Object o = argumentAdjustor.adjust(TestBean[].class, objs);
        assertTrue("1", o instanceof TestBean[]);

        TestBean[] ts = (TestBean[]) o;

        for (int i = 0; i < ts.length; i++) {
            TestBean t = ts[i];
            assertEquals("2", t.getName(), "name"+i);
        }
    }
    
    public void testAdjustMapArrayToBean() throws Exception {
        Map[] maps = new Map[5];
        for (int i = 0; i < maps.length; i++) {
            Map map = new Amf3Object();
            map.put("name", "test name");
            map.put("age", new Integer(i));
            map.put("flag", Boolean.TRUE);
            map.put("birthday", Calendar.getInstance().getTime());
            maps[i] = map;
        }

        Object o = argumentAdjustor.adjust(TestBean[].class, maps);
        assertTrue("1", o instanceof TestBean[]);

        TestBean[] ts = (TestBean[]) o;

        for (int i = 0; i < maps.length; i++) {
            Map map = maps[i];
            TestBean t = ts[i];
            assertEquals("2", t.getName(), (String) map.get("name"));
            assertEquals("3", t.getAge(), i);
            assertEquals("4", t.isFlag(), true);
            assertEquals("5", t.getBirthday(), (Date) map.get("birthday"));
        }
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}
