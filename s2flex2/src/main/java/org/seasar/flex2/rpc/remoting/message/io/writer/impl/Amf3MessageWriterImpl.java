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
package org.seasar.flex2.rpc.remoting.message.io.writer.impl;

import java.io.IOException;

import org.seasar.flex2.core.format.amf3.type.Amf3References;
import org.seasar.flex2.rpc.remoting.message.data.MessageHeader;

public class Amf3MessageWriterImpl extends AmfMessageWriterImpl {

    protected Amf3References references;

    public Amf3References getReferences() {
        return references;
    }

    public void setReferences(final Amf3References references) {
        this.references = references;
    }

    public void write() throws IOException {
        writeVersion();
        writeHeaders();
        writeBodies();
    }

    protected void clean() {
        super.clean();
        references.initialize();
    }

    protected void writeHeader(final MessageHeader header) throws IOException {
        outputStream.writeUTF(header.getName());
        outputStream.writeBoolean(header.isRequired());
        outputStream.writeInt(header.getLength());
        writeData(header.getValue());
    }

    protected void writeHeaders() throws IOException {
        final int headerNumber = message.getHeaderSize();
        outputStream.writeShort(headerNumber);
        for (int i = 0; i < headerNumber; ++i) {
            writeHeader(message.getHeader(i));
        }
    }

    protected final void writeVersion() throws IOException {
        outputStream.writeShort(message.getVersion());
    }
}