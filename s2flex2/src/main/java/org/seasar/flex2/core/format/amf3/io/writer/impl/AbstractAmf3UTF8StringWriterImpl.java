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
package org.seasar.flex2.core.format.amf3.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.seasar.flex2.core.format.amf3.Amf3Constants;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.type.CharsetType;

public abstract class AbstractAmf3UTF8StringWriterImpl extends
        Amf3IntWriterImpl implements Amf3DataWriter {

    private static final byte[] getUTF8StringBytes(final String string) {
        try {
            return string.getBytes(CharsetType.UTF8);
        } catch (final UnsupportedEncodingException e) {
            return new byte[0];
        }
    }

    protected static final void writeUTF8String(final String value,
            final DataOutputStream outputStream) throws IOException {

        final byte[] stringBytes = getUTF8StringBytes(value);
        final int stringDef = (stringBytes.length << 1)
                | Amf3Constants.OBJECT_INLINE;
        writeIntData(stringDef, outputStream);

        if (stringBytes.length > 0) {
            outputStream.write(stringBytes, 0, stringBytes.length);
        }
    }
}