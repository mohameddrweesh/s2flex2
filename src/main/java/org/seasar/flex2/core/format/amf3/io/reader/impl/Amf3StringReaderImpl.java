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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;


public class Amf3StringReaderImpl extends AbstractAmf3UTF8StringReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    protected Object readInlinedObject(int reference,
            DataInputStream inputStream) throws IOException {
        String result = readStringData(reference, inputStream);
        if (result.length() > 0) {
            addStringReference(result);
        }
        return result;
    }

    protected Object readReferencedObject(int reference,
            DataInputStream inputStream) throws IOException {
        return getStringAt(reference >>> 1);
    }
}