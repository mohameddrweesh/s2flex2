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
package org.seasar.flex2.util.converter.impl;

import org.seasar.flex2.util.converter.Converter;

public class StringIntegerConverter implements Converter {
    
    private static final String HEX_SIGN = "0x";
    
    public Object convert(Object source, Class convertClass) {
        Object retuenValue = null;
        if( source instanceof String ){
            retuenValue = convert( (String)source );
        }
        return retuenValue;
    }
    
    private final Integer convert( String number) {
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
