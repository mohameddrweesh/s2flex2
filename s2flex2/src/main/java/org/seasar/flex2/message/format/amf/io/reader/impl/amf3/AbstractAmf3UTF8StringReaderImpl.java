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
package org.seasar.flex2.message.format.amf.io.reader.impl.amf3;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.seasar.flex2.message.format.amf.Amf3Constants;
import org.seasar.flex2.message.io.charset.CharsetType;

public abstract class AbstractAmf3UTF8StringReaderImpl extends
        AbstractAmf3ObjectReaderImpl {

    protected final String readStringData(final int stringDef,
            final DataInputStream inputStream) throws IOException {
        int stringLength = stringDef >> 1;
        String str = null;
        if (stringLength > 0) {
            byte[] charArray = new byte[stringLength * 2];
            inputStream.readFully(charArray, 0, stringLength);
            str = getUTF8String(charArray, stringLength);
        } else {
            str = Amf3Constants.EMPTY_STRING;
        }

        return str;
    }
    
    private final String getUTF8String( final byte[] bytearr, final int utflen) {
        try {
            return new String(bytearr, 0, utflen, CharsetType.UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}