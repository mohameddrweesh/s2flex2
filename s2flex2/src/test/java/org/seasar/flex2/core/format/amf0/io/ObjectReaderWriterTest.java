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

import java.util.Map;

import org.seasar.flex2.core.format.amf0.type.Amf0Object;

public class ObjectReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testObject() throws Exception {
        Map value = new Amf0Object();

        value.put("test1", "testv1");
        value.put("test2", "testv2");
        value.put("test3", "testv3");
        value.put("test4", "testv4");
        value.put("test5", "testv5");

        assertEquals("1", value, getWriteReadData(value));

    }
}