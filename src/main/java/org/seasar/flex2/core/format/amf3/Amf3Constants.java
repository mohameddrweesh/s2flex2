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
package org.seasar.flex2.core.format.amf3;

public interface Amf3Constants {

    byte CLASS_DEF_INLINE = 0x02;

    byte CLASS_DEF_REFERENCE = 0x00;

    String EMPTY_STRING = "";

    byte EMPTY_STRING_DATA = 0x01;

    int INTEGER_MAX = 0xFFFFFFF;

    int INTEGER_MIN = -0xFFFFFFF;

    byte OBJECT_INLINE = 0x01;

    byte OBJECT_REFERENCE = 0x00;

    byte PROPERTY_DEF = 0x0C;

    byte PROPERTY_DEF_LIST = 0x00;

    byte PROPERTY_DEF_SINGLE = 0x04;

    byte PROPERTY_DEF_WITH_VALUE = 0x08;
}
