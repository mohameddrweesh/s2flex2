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
package org.seasar.flex2.rpc.amf.io.reader.impl;

import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.Amf3References;
import org.seasar.flex2.rpc.amf.io.reader.AmfReader;

public class Amf3ReaderImpl extends AmfReaderImpl implements AmfReader {

    private Amf3References references;

    public Amf3ReaderImpl() {
    }

    public AmfMessage read() throws IOException {
        readVersion();
        readHeader();
        readBodies();
        return message;
    }

    protected void clean() {
        super.clean();
        references.initialize();
    }

    protected void readHeader() throws IOException {

        int headerCount = inputStream.readUnsignedShort();
        for (int i = 0; i < headerCount; ++i) {
            inputStream.readUTF();
            inputStream.readByte();
            inputStream.readInt();
            readData();
        }
    }

    protected void readVersion() throws IOException {
        message.setVersion(inputStream.readUnsignedShort());
    }

    public Amf3References getReferences() {
        return references;
    }

    public void setReferences(Amf3References references) {
        this.references = references;
    }
}