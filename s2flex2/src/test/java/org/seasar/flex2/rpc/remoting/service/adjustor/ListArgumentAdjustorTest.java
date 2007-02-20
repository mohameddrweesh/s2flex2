/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf3.type.Amf3Object;

public class ListArgumentAdjustorTest extends S2TestCase {

    private static String PATH = "ListArgumentAdjustorTest.dicon";

    private ArgumentAdjustor argumentAdjustor;

    public void testIsTarget() throws Exception {
        assertNotNull(argumentAdjustor);
        Map map = new HashMap();
        TestBean[] ts = new TestBean[] {};
        assertFalse("1", argumentAdjustor.isTarget(TestBean[].class, map));
        assertFalse("2", argumentAdjustor.isTarget(TestBean[].class, ts));
        assertFalse("3", argumentAdjustor.isTarget(List.class, map));
        assertTrue("4", argumentAdjustor.isTarget(List.class, ts));
        assertTrue("5", argumentAdjustor.isTarget(ArrayList.class, ts));
        assertTrue("6", argumentAdjustor.isTarget(LinkedList.class, ts));
        // assertTrue("7",argumentAdjustor.isTarget(Sin.class, ts));
    }

    public void testAdjustList() throws Exception {

        List l = new ArrayList(5);

        for (int i = 0; i < l.size(); i++) {
            l.add(i, "name" + i);
        }

        Object o = argumentAdjustor.adjust(Map.class, l);
        assertTrue("1", o instanceof List);

        List l2 = (List) o;

        for (int i = 0; i < l.size(); i++) {
            assertEquals("2", l2.get(i), l.get(i));
        }
    }

    public void testAdjustList2() throws Exception {
        List l = new ArrayList(5);
        for (int i = 0; i < l.size(); i++) {
            TestBean tb = new TestBean();
            tb.setName("name" + i);
            l.add(i, tb);
        }

        Object o = argumentAdjustor.adjust(Map.class, l);
        assertTrue("1", o instanceof List);

        List l2 = (List) o;
        for (int i = 0; i < l2.size(); i++) {
            TestBean t = (TestBean) l2.get(i);
            assertEquals("2", t.getName(), "name" + i);
        }

    }

    public void testAdjustList3() throws Exception {
        Object[] strs = new Object[5];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = "name" + i;
        }
        Object o = argumentAdjustor.adjust(List.class, strs);
        assertTrue("1", o instanceof List);

        List l2 = (List) o;

        for (int i = 0; i < strs.length; i++) {
            assertEquals("2", l2.get(i), strs[i]);
        }
    }

    public void testAdjustCustomArray() throws Exception {
        Object[] objs = new Object[5];
        for (int i = 0; i < objs.length; i++) {
            TestBean tb = new TestBean();
            tb.setName("name" + i);
            objs[i] = tb;
        }

        Object o = argumentAdjustor.adjust(List.class, objs);
        assertTrue("1", o instanceof List);

        List l = (List) o;

        for (int i = 0; i < l.size(); i++) {
            TestBean t = (TestBean) l.get(i);
            assertEquals("2", t.getName(), "name" + i);
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
        Object o = argumentAdjustor.adjust(List.class, maps);
        assertTrue("1", o instanceof List);

        List l = (List) o;
        for (int i = 0; i < maps.length; i++) {
            Map map = maps[i];
            Amf3Object t = (Amf3Object) l.get(i);

            assertEquals("2", t.get("name"), (String) map.get("name"));
            assertEquals("3", (Integer) t.get("age"), (Integer) map.get("age"));
            assertEquals("4", (Boolean) t.get("flag"), Boolean.TRUE);
            assertEquals("5", (Date) t.get("birthday"), (Date) map
                    .get("birthday"));
        }
    }

    protected void setUp() throws Exception {
        include(PATH);
    }

    protected void tearDown() throws Exception {
    }
}
