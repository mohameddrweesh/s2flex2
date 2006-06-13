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
package org.seasar.flex2.message.format.amf.io.writer.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import org.seasar.flex2.message.format.amf.Amf3Constants;
import org.seasar.flex2.message.format.amf.type.Amf3TypeDef;

public class Amf3DateWriterImpl extends AbstractAmf3ObjectWriterImpl {

    public int getDataTypeValue() {
        return Amf3TypeDef.DATE;
    }

    protected void writeInlineObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeDateData((Date) object, outputStream);
    }

    private final void writeDateData(final Date date,
            DataOutputStream outputStream) throws IOException {
        addObjectReference(date);
        outputStream.writeByte(Amf3Constants.OBJECT_INLINE);
        outputStream.writeDouble(((Date) date).getTime());
    }
}