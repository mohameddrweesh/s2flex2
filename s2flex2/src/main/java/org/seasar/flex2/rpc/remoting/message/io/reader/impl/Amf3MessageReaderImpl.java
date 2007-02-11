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
package org.seasar.flex2.rpc.remoting.message.io.reader.impl;

import java.io.IOException;

import org.seasar.flex2.core.format.amf3.type.Amf3References;
import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;

public class Amf3MessageReaderImpl extends AmfMessageReaderImpl implements
        MessageReader {

    private Amf3References references;

    public Amf3MessageReaderImpl() {
    }

    public Amf3References getReferences() {
        return references;
    }

    public Message read() throws IOException {
        readVersion();
        readHeader();
        readBodies();
        return message;
    }

    public void setReferences(final Amf3References references) {
        this.references = references;
    }

    protected void clean() {
        super.clean();
        references.initialize();
    }

    protected void readHeader() throws IOException {
        final int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            final String name = inputStream.readUTF();
            final boolean isRequired = inputStream.readBoolean();
            inputStream.readInt(); // length
            message.addHeader(messageHeaderFactory.createHeader(name,
                    readData(), isRequired));
        }
    }
/*
AmfMessageReaderImplへ移動
    protected final void readVersion() throws IOException {
        message.setVersion(inputStream.readUnsignedShort());
    }
    */
}