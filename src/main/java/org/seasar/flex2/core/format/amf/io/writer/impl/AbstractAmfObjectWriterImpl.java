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
package org.seasar.flex2.core.format.amf.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf.io.writer.factory.AmfDataWriterFactory;
import org.seasar.flex2.core.format.amf.type.AmfSharedObject;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;
import org.seasar.flex2.core.format.amf.type.factory.AmfSharedObjectFactory;

public abstract class AbstractAmfObjectWriterImpl implements AmfDataWriter {

    private AmfSharedObjectFactory sharedObjectFactory;

    private AmfDataWriterFactory writerFactory;

    public void setSharedObjectFactory(
            AmfSharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }

    public void setWriterFactory(AmfDataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void write(final Object value, final DataOutputStream outputStream)
            throws IOException {
        int index = getSharedObject().getSharedIndex(value);
        if (index >= 0) {
            writeSharedIndex(index, outputStream);
        } else {
            writeObjectData(value, outputStream);
        }
    }

    protected final AmfSharedObject getSharedObject() {
        return sharedObjectFactory.createSharedObject();
    }

    protected final void writeData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        writerFactory.createDataWriter(value).write(value, outputStream);
    }

    protected abstract void writeObjectData(Object value,
            DataOutputStream outputStream) throws IOException;

    protected final void writeSharedIndex(final int index,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(AmfTypeDef.FLUSHED_SHARED_OBJECT);
        outputStream.writeShort(index);
    }
}