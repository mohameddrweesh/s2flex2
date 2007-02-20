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

import org.seasar.flex2.core.format.amf3.Amf3IntegerConstants;

public class IntegerReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testInteger() throws Exception {

        Integer value1 = new Integer(Integer.MAX_VALUE);

        assertEquals("3", new Double(value1.intValue()),
                getWriteReadData(value1));

        value1 = new Integer(Integer.MIN_VALUE);

        assertEquals("4", new Double(value1.intValue()),
                getWriteReadData(value1));
    }

    public void testAmf3Integer() throws Exception {

        Integer value1 = new Integer(Amf3IntegerConstants.INTEGER_MAX);

        assertEquals("3", new Integer(value1.intValue()),
                getWriteReadData(value1));

        value1 = new Integer(Amf3IntegerConstants.INTEGER_MIN);

        assertEquals("4", new Integer(value1.intValue()),
                getWriteReadData(value1));
    }

    public void testNegativeIntegers() throws Exception {
        Integer[] values = new Integer[29];

        for (int i = 0; i < 29; i++) {
            values[i] = new Integer(Amf3IntegerConstants.INTEGER_MIN >> i);
        }

        Object[] values1 = (Object[]) getWriteReadData(values);
        assertEquals("2", values.length, values1.length);

        for (int i = 0; i < values1.length; i++) {
            assertEquals("2", ((Integer) values[i]).intValue(),
                    ((Integer) values1[i]).intValue());
        }
    }

    public void testIntegers() throws Exception {
        Integer[] values = new Integer[29];

        for (int i = 0; i < 29; i++) {
            values[i] = new Integer(Amf3IntegerConstants.INTEGER_MAX >>> i);
        }

        Object[] values1 = (Object[]) getWriteReadData(values);
        assertEquals("2", values.length, values1.length);

        for (int i = 0; i < values1.length; i++) {
            assertEquals("2", ((Integer) values[i]).intValue(),
                    ((Integer) values1[i]).intValue());
        }
    }
}