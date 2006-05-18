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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.rpc.amf.data.AmfDataType;

public class AmfObjectWriterImpl extends AbstractAmfObjectWriterImpl {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        write((Map) value, outputStream);
    }

    protected void write(Map value, DataOutputStream outputStream)
            throws IOException {
        int index = getSharedObject().getSharedIndex(value);
        if (index >= 0) {
            writeSharedIndex(index, outputStream);
            return;
        }
        getSharedObject().addSharedObject(value);
        outputStream.writeByte(AmfDataType.OBJECT);
        for (Iterator i = value.entrySet().iterator(); i.hasNext();) {
            Map.Entry e = (Map.Entry) i.next();
            String propertyName = String.valueOf(e.getKey());
            outputStream.writeUTF(propertyName);
            writeData(e.getValue(), outputStream);
        }
        outputStream.writeShort(0);
        outputStream.writeByte(9);
    }
}