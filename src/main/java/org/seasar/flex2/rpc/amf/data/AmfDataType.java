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

public final class AmfDataType {

    public static final byte UNKNOWN = -1;

    public static final byte NUMBER = 0;

    public static final byte BOOLEAN = 1;

    public static final byte STRING = 2;

    public static final byte OBJECT = 3;

    public static final byte NULL = 5;

    public static final byte UNDEFINED = 6;

    public static final byte FLUSHED_SHARED_OBJECT = 7;

    public static final byte ARRAY_SHARED_OBJECT = 8;

    public static final byte EOM = 9;

    public static final byte ARRAY = 10;

    public static final byte DATE = 11;

    public static final byte AS_OBJECT = 13;

    public static final byte XML = 15;

    public static final byte CUSTOM_CLASS = 16;

    public static final String TYPE_NUMBER = "AMF_NUMBER";

    public static final String TYPE_BOOLEAN = "AMF_BOOLEAN";

    public static final String TYPE_STRING = "AMF_STRING";

    public static final String TYPE_OBJECT = "AMF_OBJECT";

    public static final String TYPE_NULL = "AMF_NULL";

    public static final String TYPE_FLUSHED_SHARED_OBJECT = "AMF_FLUSHED_SHARED_OBJECT";

    public static final String TYPE_ARRAY_SHARED_OBJECT = "AMF_ARRAY_SHARED_OBJECT";

    public static final String TYPE_EOM = "AMF_END_OF_MARK";

    public static final String TYPE_ARRAY = "AMF_ARRAY";

    public static final String TYPE_DATE = "AMF_DATE";

    public static final String TYPE_AS_OBJECT = "AMF_AS_OBJECT";

    public static final String TYPE_XML = "AMF_XML";

    public static final String TYPE_CUSTOM_CLASS = "AMF_CUSTOM_CLASS";

    public static final String TYPE_UNKNOWN = "AMF_UNKNOWN";

    private AmfDataType() {
    }

    public static final String toString(byte dataType) {

        switch (dataType) {
            case NUMBER:
                return TYPE_NUMBER;
            case BOOLEAN:
                return TYPE_BOOLEAN;
            case STRING:
                return TYPE_STRING;
            case OBJECT:
                return TYPE_OBJECT;
            case FLUSHED_SHARED_OBJECT:
                return TYPE_FLUSHED_SHARED_OBJECT;
            case ARRAY_SHARED_OBJECT:
                return TYPE_ARRAY_SHARED_OBJECT;
            case EOM:
                return TYPE_EOM;
            case ARRAY:
                return TYPE_ARRAY;
            case DATE:
                return TYPE_DATE;
            case AS_OBJECT:
                return TYPE_AS_OBJECT;
            case XML:
                return TYPE_XML;
            case CUSTOM_CLASS:
                return TYPE_CUSTOM_CLASS;
            case NULL:
            case UNDEFINED:
            default:
                return TYPE_NULL;
        }
    }
}
