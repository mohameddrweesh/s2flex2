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
package org.seasar.flex2.core.format.amf.io.reader.binder.impl;

import org.seasar.flex2.core.format.amf.io.reader.binder.DataBinder;

public class IntegerDataBinder implements DataBinder {

    private static final String HEX_SIGN = "0x";

    private final static boolean isBindClass(final Class bindClass) {
        return (bindClass == Integer.class) || (bindClass == int.class);
    }

    private final static boolean isTarget(final Object targetValue) {
        return (targetValue != null)
                && (targetValue.getClass() == String.class);
    }

    public Object bind(final Object source, final Class bindClass) {
        return doBind((String) source);
    }

    public boolean isTarget(final Object targetValue, final Class bindClass) {
        return isBindClass(bindClass) && isTarget(targetValue);
    }

    private final Integer doBind(final String number) {
        int returnValue = 0;
        do {
            if (number == null || number.length() == 0) {
                break;
            }
            try {
                if (number.indexOf(HEX_SIGN) == 0) {
                    returnValue = Integer.parseInt(number.substring(2), 16);
                } else {
                    returnValue = Integer.parseInt(number, 10);
                }
            } catch (NumberFormatException e) {
                returnValue = 0;
            }
        } while (false);

        return new Integer(returnValue);
    }
}
