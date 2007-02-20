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
package org.seasar.flex2.core.format.amf0.type;

public interface Amf0TypeDef {

    byte UNKNOWN = -1;

    byte NUMBER = 0x00;

    byte BOOLEAN = 0x01;

    byte STRING = 0x02;

    byte OBJECT = 0x03;

    byte MOVIECLIP = 0x04;

    byte NULL = 0x05;

    byte UNDEFINED = 0x06;

    byte REFERENCE = 0x07;

    byte MIXEDARRAY = 0x08;

    byte EOO = 0x09;

    byte ARRAY = 0x0A;

    byte DATE = 0x0B;

    byte LONGSTRING = 0x0C;

    byte AS_OBJECT = 0xD;

    byte RECORDSET = 0x0E;

    byte XMLSTRING = 0x0F;

    byte TYPEDOBJECT = 0x10;

    byte AMF3_DATA_MARKER = 0x11;
}
