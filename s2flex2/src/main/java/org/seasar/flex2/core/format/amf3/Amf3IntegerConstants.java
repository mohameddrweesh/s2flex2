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
package org.seasar.flex2.core.format.amf3;

public interface Amf3IntegerConstants {

    int INTEGER_MAX_DATA_BYTES = 4;
    
    int INTEGER_DATA_MASK = 0x7F;

    int INTEGER_MAX = 0xFFFFFFF;

    int INTEGER_MIN = -0xFFFFFFF;

    int INTEGER_INCLUDE_NEXT_SIGN = 0x80;
    
    int INTEGER_NAGATIVE_SIGN = 0xC0;
}
