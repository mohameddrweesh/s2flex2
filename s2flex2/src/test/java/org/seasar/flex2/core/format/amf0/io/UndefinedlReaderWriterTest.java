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

import java.io.DataInputStream;

import org.seasar.flex2.core.format.amf.io.AmfDataReader;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;

public class UndefinedlReaderWriterTest extends AbstractReaderWriterS2TestCase {

    public void testUndefined() throws Exception {

        DataInputStream inputStream = createInputStream(new byte[] { Amf0TypeDef.UNDEFINED });
        byte dataType = inputStream.readByte();

        AmfDataReader reader = readerFactory.createDataReader(dataType);

        assertEquals("1", null, reader.read(inputStream));
    }
}