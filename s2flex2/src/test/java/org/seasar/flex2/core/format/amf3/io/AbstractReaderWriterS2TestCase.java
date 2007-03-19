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
package org.seasar.flex2.core.format.amf3.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.format.amf3.io.reader.factory.Amf3DataReaderFactory;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.factory.Amf3DataWriterFactory;

public class AbstractReaderWriterS2TestCase extends S2TestCase {

    protected final static String PATH = "amf3.dicon";

    private ByteArrayOutputStream out;

    protected Amf3DataReaderFactory readerFactory;

    protected Amf3DataWriterFactory writerFactory;

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

        Amf3DataWriter writer = writerFactory.createAmf3DataWriter(value);
        writer.writeAmf3Data(value, outputStream);

        DataInputStream inputStream = createInputStream(out.toByteArray());
        byte dataType = inputStream.readByte();
        AmfDataReader reader = readerFactory.createAmf3DataReader(dataType);

        return reader.read(inputStream);
    }

    protected void setUp() throws Exception {
        include(PATH);
    }
}