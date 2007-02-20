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
package org.seasar.flex2.core.format.amf0.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf0.io.writer.factory.Amf0DataWriterFactory;
import org.seasar.flex2.core.format.amf0.type.Amf0SharedObject;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;
import org.seasar.flex2.core.format.amf0.type.factory.Amf0SharedObjectFactory;

public abstract class AbstractAmfObjectWriterImpl implements Amf0DataWriter {

    private Amf0SharedObjectFactory sharedObjectFactory;

    private Amf0DataWriterFactory writerFactory;

    public void setSharedObjectFactory(
            final Amf0SharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }

    public void setWriterFactory(final Amf0DataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void write(final Object value, final DataOutputStream outputStream)
            throws IOException {
        final int index = getSharedObject().getSharedIndex(value);
        if (index >= 0) {
            writeSharedIndex(index, outputStream);
        } else {
            writeObjectData(value, outputStream);
        }
    }

    protected final Amf0SharedObject getSharedObject() {
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
        outputStream.writeByte(Amf0TypeDef.REFERENCE);
        outputStream.writeShort(index);
    }
}