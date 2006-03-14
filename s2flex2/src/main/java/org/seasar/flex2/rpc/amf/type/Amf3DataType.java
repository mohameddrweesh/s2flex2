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
package org.seasar.flex2.rpc.amf.type;

public final class Amf3DataType {
	
	public static final byte AMF3_DATA_MARKER = 0x11;

	public static final byte NULL = 0x01;
	public static final byte BOOLEAN_FALSE = 0x02;
	public static final byte BOOLEAN_TRUE = 0x03;
	public static final byte INTEGER = 0x04;
	public static final byte NUMBER = 0x05;
	public static final byte STRING = 0x06;
	public static final byte DATE = 0x08;
	public static final byte ARRAY = 0x09;
	public static final byte OBJECT = 0x0A;
	public static final byte XML = 0x0B;
	
    public static final byte OBJECT_REFERENCE = 0x00;
    public static final byte OBJECT_INLINE = 0x01;
    
    public static final byte OBJECT_SERIALIZED = 0x04;
    public static final byte OBJECT_DESERIALIZED = 0x08;
    
    public static final byte CLASS_DEF_REFERENCE = 0x00;
    public static final byte CLASS_DEF_INLINE = 0x02;
    
    public static final int INTEGER_VARIABLED_FLAG = 0x80;
    public static final int INTEGRR_MAX = 0xFFFFFFF;
    public static final int INTEGRR_MIN = -0xFFFFFFF;
    public static final int INTEGER_NEGATIVE_SING = 0x40;
    
    public static final byte EMPTY_STRING = 0x01;
    
	private Amf3DataType() {
	}
	
	public static final String toString(byte dataType) {
		switch (dataType) {
			case NULL:
				return "NULL";
			case BOOLEAN_FALSE:
				return "BOOLEAN_FALSE";
			case BOOLEAN_TRUE:
				return "BOOLEAN_TRUE";			
			case INTEGER:
				return "INTEGER";
			case NUMBER:
				return "NUMBER";
			case STRING:
				return "STRING";
			case DATE:
				return "DATE";
			case ARRAY:
				return "ARRAY";
			case OBJECT:
				return "OBJECT";
			default:
				return "UNKNOWN[" + dataType + "]";
		}
	}
}
