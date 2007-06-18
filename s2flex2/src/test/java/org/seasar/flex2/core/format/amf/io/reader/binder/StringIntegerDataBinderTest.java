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
package org.seasar.flex2.core.format.amf.io.reader.binder;

import org.seasar.extension.unit.S2TestCase;

public class StringIntegerDataBinderTest extends S2TestCase {

    private DataBinder stringIntegerDataBinder;
    
    protected void setUp() throws Exception {
        super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
    }
    
    public void testStringToInt() {

        int expectInt = 100000;

        String expectIntStr = "" + expectInt;

        Integer bindedInteger = (Integer) stringIntegerDataBinder.bind(
                expectIntStr, int.class);

        assertEquals("1", expectInt, bindedInteger.intValue());
    }

    public void testStringToInteger() {

        int expectInt = 100000;

        String expectIntStr = "" + expectInt;

        Integer bindedInteger = (Integer) stringIntegerDataBinder.bind(
                expectIntStr, Integer.class);

        assertEquals("1", expectInt, bindedInteger.intValue());
    }

    public void testHexStringToInteger() {

        int expectInt = 100000;

        String expectIntStr = "0x" + Integer.toHexString(expectInt);

        Integer bindedInteger = (Integer) stringIntegerDataBinder.bind(
                expectIntStr, Integer.class);

        assertEquals("1", expectInt, bindedInteger.intValue());
    }

    /**
     * @return Returns the stringIntegerDataBinder.
     */
    public DataBinder getStringIntegerDataBinder() {
        return stringIntegerDataBinder;
    }

    /**
     * @param stringIntegerDataBinder The stringIntegerDataBinder to set.
     */
    public void setStringIntegerDataBinder(DataBinder stringIntegerDataBinder) {
        this.stringIntegerDataBinder = stringIntegerDataBinder;
    }
}
