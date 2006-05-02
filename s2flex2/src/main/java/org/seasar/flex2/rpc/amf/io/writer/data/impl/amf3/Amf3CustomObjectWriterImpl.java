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

import org.seasar.flex2.rpc.amf.io.writer.data.Amf3ObjectWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.Amf3ObjectWriterFactory;

public abstract class Amf3CustomObjectWriterImpl extends Amf3ObjectWriterImpl {

    protected Amf3ObjectWriterFactory writerFactory;

    public void setWriterFactory(Amf3ObjectWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    protected void writeEntryData(Object value, DataOutputStream outputStream)
            throws IOException {
        Amf3ObjectWriter dataWriter = writerFactory
                .createObjectDataWriter(value);
        dataWriter.writeData(value, outputStream);
    }
}