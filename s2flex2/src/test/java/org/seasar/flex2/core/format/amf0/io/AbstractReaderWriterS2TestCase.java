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
package org.seasar.flex2.core.format.amf0.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf0.io.reader.factory.Amf0DataReaderFactory;
import org.seasar.flex2.core.format.amf0.io.writer.factory.Amf0DataWriterFactory;
import org.seasar.flex2.core.io.AmfDataReader;
import org.seasar.flex2.core.io.AmfDataWriter;

public class AbstractReaderWriterS2TestCase extends S2TestCase {

    protected final static String PATH = "amf0.dicon";

    private ByteArrayOutputStream out;

    protected Amf0DataReaderFactory readerFactory;

    protected Amf0DataWriterFactory writerFactory;

    protected DataInputStream createInputStream(final byte[] bytes) {

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        return new DataInputStream(in);
    }

    private DataOutputStream createOutputStream() {
        if (out == null) {
            out = new ByteArrayOutputStream();
        } else {
            out.reset();
        }
        return new DataOutputStream(out);
    }

    protected Object getWriteReadData(final Object value) throws Exception {
        DataOutputStream outputStream = createOutputStream();

        AmfDataWriter writer = writerFactory.createDataWriter(value);
        writer.write(value, outputStream);

        DataInputStream inputStream = createInputStream(out.toByteArray());
        byte dataType = inputStream.readByte();
        AmfDataReader reader = readerFactory.createDataReader(dataType);

        return reader.read(inputStream);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }
}