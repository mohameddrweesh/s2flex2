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

import org.seasar.flex2.core.format.amf3.io.reader.ASObjectReader;
import org.seasar.flex2.core.format.amf3.type.Amf3Object;

public class Amf3ASObjectReaderImpl extends AbstractAmf3TypedObjectReaderImpl
        implements ASObjectReader {

    public final Object read(final DataInputStream inputStream)
            throws IOException {

        final Amf3Object object = new Amf3Object();
        addObjectReference(object);
        String propertyName;
        while (true) {
            propertyName = (String) stringReader.read(inputStream);
            if (propertyName.length() <= 0) {
                break;
            }
            object.put(propertyName, readPropertyValue(inputStream));
        }
        return object;
    }

    protected Object readInlinedObject(int reference,
            DataInputStream inputStream) throws IOException {
        return null;
    }

    protected Object readReferencedObject(int reference,
            DataInputStream inputStream) throws IOException {
        return null;
    }
}