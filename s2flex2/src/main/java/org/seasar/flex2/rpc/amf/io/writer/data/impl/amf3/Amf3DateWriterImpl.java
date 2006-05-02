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
import java.util.Date;

import org.seasar.flex2.rpc.amf.type.Amf3DataType;

public class Amf3DateWriterImpl extends Amf3ObjectWriterImpl {

    public int getObjectType() {
        return Amf3DataType.DATE;
    }

    protected void processWriteObjectData(Object object,
            DataOutputStream outputStream) throws IOException {
        writeDateData((Date) object, outputStream);
    }

    private final void writeDateData(final Date date,
            DataOutputStream outputStream) throws IOException {
        addObjectReference(date);
        outputStream.writeByte(Amf3DataType.OBJECT_INLINE);
        outputStream.writeDouble(((Date) date).getTime());
    }
}