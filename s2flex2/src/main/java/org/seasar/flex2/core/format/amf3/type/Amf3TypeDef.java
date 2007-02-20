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
package org.seasar.flex2.core.format.amf3.type;

public interface Amf3TypeDef {

    byte UNKNOWN = -1;

    byte UNDEFINED = 0x00;

    byte NULL = 0x01;

    byte BOOLEAN_FALSE = 0x02;

    byte BOOLEAN_TRUE = 0x03;

    byte INTEGER = 0x04;

    byte NUMBER = 0x05;

    byte STRING = 0x06;

    byte XML = 0x07;

    byte DATE = 0x08;

    byte ARRAY = 0x09;

    byte OBJECT = 0x0A;

    byte XML_STRING = 0x0B;

    byte BYTEARRAY = 0x0C;

    /*
     * 
     * public static final String TYPE_NULL = "AMF3_NULL";
     * 
     * public static final String TYPE_BOOLEAN = "AMF3_BOOLEAN";
     * 
     * public static final String TYPE_BOOLEAN_FALSE = "AMF3_BOOLEAN_TRUE";
     * 
     * public static final String TYPE_BOOLEAN_TRUE = "AMF3_BOOLEAN_FALSE";
     * 
     * public static final String TYPE_INTEGER = "AMF3_INTEGER";
     * 
     * public static final String TYPE_NUMBER = "AMF3_NUMBER";
     * 
     * public static final String TYPE_BIGNUMBER = "AMF3_BIGNUMBER";
     * 
     * public static final String TYPE_STRING = "AMF3_STRING";
     * 
     * public static final String TYPE_OBJECT = "AMF3_OBJECT";
     * 
     * public static final String TYPE_ARRAY = "AMF3_ARRAY";
     * 
     * public static final String TYPE_DATE = "AMF3_DATE";
     * 
     * public static final String TYPE_XML = "AMF3_XML";
     * 
     * public static final String TYPE_BYTEARRAY = "AMF3_BYTEARRAY";
     * 
     * public static final String TYPE_AMF3_DATA_MARKER = "AMF3_DATA_MARKER";
     * 
     * private Amf3TypeDef() { }
     * 
     * public static final String toString(final byte dataType) { switch
     * (dataType) {
     * 
     * case BOOLEAN_FALSE: return TYPE_BOOLEAN_FALSE;
     * 
     * case BOOLEAN_TRUE: return TYPE_BOOLEAN_TRUE;
     * 
     * case INTEGER: return TYPE_INTEGER;
     * 
     * case NUMBER: return TYPE_NUMBER;
     * 
     * case STRING: return TYPE_STRING;
     * 
     * case DATE: return TYPE_DATE;
     * 
     * case ARRAY: return TYPE_ARRAY;
     * 
     * case OBJECT: return TYPE_OBJECT;
     * 
     * case XML: return TYPE_XML;
     * 
     * case BYTEARRAY: return TYPE_BYTEARRAY;
     * 
     * case AMF3_DATA_MARKER: return TYPE_AMF3_DATA_MARKER;
     * 
     * case NULL: case UNDEFINED: default: return TYPE_NULL; } }
     */
}
