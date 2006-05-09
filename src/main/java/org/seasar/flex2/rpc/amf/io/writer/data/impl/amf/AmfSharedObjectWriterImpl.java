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

import org.seasar.flex2.rpc.amf.io.AmfSharedObject;
import org.seasar.flex2.rpc.amf.io.factory.AmfSharedObjectFactory;
import org.seasar.flex2.rpc.amf.io.writer.data.AmfDataWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.factory.AmfDataWriterFactory;
import org.seasar.flex2.rpc.amf.type.AmfDataType;

public abstract class AmfSharedObjectWriterImpl implements AmfDataWriter {

    protected AmfSharedObjectFactory sharedObjectFactory;

    protected AmfDataWriterFactory writerFactory;

    public void setSharedObjectFactory(
            AmfSharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }

    public void setWriter(AmfDataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    protected final AmfSharedObject getSharedObject() {
        return sharedObjectFactory.createSharedObject();
    }

    protected void writeData( Object value, DataOutputStream outputStream) throws IOException{
        AmfDataWriter dataWriter = writerFactory.createObjectWriter(value);
        dataWriter.write(value, outputStream);
    }
    
    protected final void writeSharedIndex(int index,
            DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(AmfDataType.FLASHED_SHARED_OBJECT);
        outputStream.writeShort(index);
    }
}