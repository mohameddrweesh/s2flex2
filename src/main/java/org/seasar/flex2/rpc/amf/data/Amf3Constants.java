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

public interface Amf3Constants {

    static final byte CLASS_DEF_INLINE = 0x02;

    static final byte CLASS_DEF_REFERENCE = 0x00;

    static final String EMPTY_STRING = "";

    static final byte EMPTY_STRING_DATA = 0x01;

    static final byte INTEGER_DATA_MAX_BYTES = 4;

    static final int INTEGER_INCLUDE_NEXT_BYTE = 0x80;

    static final int INTEGER_NEGATIVE_SING = 0x40;

    static final int INTEGRR_MAX = 0xFFFFFFF;

    static final int INTEGRR_MIN = -0xFFFFFFF;

    static final byte OBJECT_INLINE = 0x01;

    static final byte OBJECT_REFERENCE = 0x00;

    static final byte PROPERTY_DEF = 0x0C;

    static final byte PROPERTY_DEF_LIST = 0x00;

    static final byte PROPERTY_DEF_SINGLE = 0x04;

    static final byte PROPERTY_DEF_WITH_VALUE = 0x08;

}
