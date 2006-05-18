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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.Amf3Constants;
import org.seasar.flex2.rpc.amf.io.util.Amf3DataUtil;

public abstract class AbstractAmf3UTF8StringWriterImpl extends
        AbstractAmf3IntWriterImpl {

    protected final void writeUTF8String(final String value,
            final DataOutputStream outputStream) throws IOException {

        byte[] bytearr = Amf3DataUtil.toUTF8StringBytes(value);
        int stringDef = (bytearr.length << 1) | Amf3Constants.OBJECT_INLINE;
        writeIntData(stringDef, outputStream);

        if (bytearr.length > 0) {
            outputStream.write(bytearr, 0, bytearr.length);
        }
    }
}