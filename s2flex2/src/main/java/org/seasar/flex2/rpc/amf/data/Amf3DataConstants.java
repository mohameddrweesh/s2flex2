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
package org.seasar.flex2.rpc.amf.data;

public interface Amf3DataConstants {

    public static final byte CLASS_DEF_INLINE = 0x02;

    public static final byte CLASS_DEF_REFERENCE = 0x00;

    public static final byte EMPTY_STRING_DATA = 0x01;
    
    public static final String EMPTY_STRING = "";

    public static final byte INTEGER_DATA_MAX_BYTES = 4;

    public static final int INTEGER_NEGATIVE_SING = 0x40;

    public static final int INTEGER_INCLUDE_NEXT_BYTE = 0x80;

    public static final int INTEGRR_MAX = 0xFFFFFFF;

    public static final int INTEGRR_MIN = -0xFFFFFFF;

    public static final byte OBJECT_INLINE = 0x01;

    public static final byte OBJECT_REFERENCE = 0x00;
    
    public static final byte UNTYPED_OBJECT = 0x08;
}
