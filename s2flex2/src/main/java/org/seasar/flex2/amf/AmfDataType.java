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
package org.seasar.flex2.amf;

public final class AmfDataType {

	public static final byte UNKNOWN = -1;
	public static final byte NUMBER = 0;
	public static final byte BOOLEAN = 1;
	public static final byte STRING = 2;
	public static final byte OBJECT = 3;
	public static final byte NULL = 5;
	public static final byte FLASHED_SHARED_OBJECT = 7;
	public static final byte ARRAY_SHARED_OBJECT = 8;
	public static final byte EOM = 9;
	public static final byte ARRAY = 10;
	public static final byte DATE = 11;
	public static final byte AS_OBJECT = 13;
	public static final byte XML = 15;
	public static final byte CUSTOM_CLASS = 16;
	
	private AmfDataType() {
	}
	
	public static final String toString(byte dataType) {
		switch (dataType) {
			case NUMBER:
				return "NUMBER";
			case BOOLEAN:
				return "BOOLEAN";
			case STRING:
				return "STRING";
			case OBJECT:
				return "OBJECT";
			case NULL:
				return "NULL";
			case FLASHED_SHARED_OBJECT:
				return "FLUSHED_SHARED_OBJECT";
			case ARRAY_SHARED_OBJECT:
				return "ARRAY_SHARED_OBJECT";
			case EOM:
				return "ENF_OF_MARK";
			case ARRAY:
				return "ARRAY";
			case DATE:
				return "DATE";
			case AS_OBJECT:
				return "AS_OBJECT";
			case XML:
				return "XML";
			case CUSTOM_CLASS:
				return "CUSTOM_CLASS";
			default:
				return "UNKNOWN";
		}
	}
}
