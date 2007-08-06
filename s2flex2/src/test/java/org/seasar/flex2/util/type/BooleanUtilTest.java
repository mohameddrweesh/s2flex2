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
package org.seasar.flex2.util.type;

import junit.framework.TestCase;

public class BooleanUtilTest extends TestCase {
    public void testToBoolean() {
        assertTrue("1", BooleanUtil.toBoolean("True"));
        assertFalse("2", BooleanUtil.toBoolean(null));
        assertTrue("3", BooleanUtil.toBoolean("tRue"));
        assertFalse("4", BooleanUtil.toBoolean("false"));
        assertTrue("5", BooleanUtil.toBoolean("trUe"));
        assertTrue("6", BooleanUtil.toBoolean("truE"));
        assertTrue("7", BooleanUtil.toBoolean("TRUE"));
        assertTrue("8", BooleanUtil.toBoolean("TRUe"));
        assertTrue("9", BooleanUtil.toBoolean("TRue"));
        assertTrue("10", BooleanUtil.toBoolean("TruE"));
        assertTrue("11", BooleanUtil.toBoolean("trUE"));
        assertTrue("11", BooleanUtil.toBoolean("tRUE"));
    }
}
