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

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;

public class Amf3ArrayReaderImpl extends AbstractAmf3TypedObjectReaderImpl {

    private AmfDataReader stringReader;
    
    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Object[] readArrayData(final int arrayDef,
            final DataInputStream inputStream) throws IOException {
        final int arrayLength = arrayDef >> 1;
        final Object[] array = new Object[arrayLength];
        addObjectReference(array);
        
        readHashArrayData(inputStream);

        for (int i = 0; i < arrayLength; i++) {
            array[i] = readPropertyValue(inputStream);
        }
        return array;
    }

    private final void readHashArrayData(
            final DataInputStream inputStream) throws IOException {
        String propertyName;
        while (true) {
            propertyName = (String) stringReader.read(inputStream);
            if (propertyName.length() <= 0) {
                break;
            }
            readPropertyValue(inputStream);
        }
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readArrayData(reference, inputStream);
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) {
        return getObjectAt(reference >>> 1);
    }

    public void setStringReader(AmfDataReader stringReader) {
        this.stringReader = stringReader;
    }
}