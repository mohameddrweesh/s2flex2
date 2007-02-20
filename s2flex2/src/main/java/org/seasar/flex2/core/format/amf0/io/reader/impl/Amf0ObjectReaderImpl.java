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
package org.seasar.flex2.core.format.amf0.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf0.Amf0Constants;
import org.seasar.flex2.core.format.amf0.type.Amf0Object;
import org.seasar.flex2.core.format.amf0.type.Amf0TypeDef;

public class Amf0ObjectReaderImpl extends AbstractAmf0TypedObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Object readCustomClass(final Amf0Object amfObject) {
        Object result = amfObject;
        if (amfObject.containsKey(Amf0Constants.REMOTE_CLASS)) {
            result = translateBean(amfObject);
        }
        return result;
    }

    private final Object readObject(final DataInputStream inputStream)
            throws IOException {
        final Amf0Object amfObject = new Amf0Object();
        addSharedObject(amfObject);

        readProperties(inputStream, amfObject);

        return readCustomClass(amfObject);
    }

    private final void readProperties(final DataInputStream inputStream,
            final Amf0Object amfObject) throws IOException {
        while (true) {
            final String key = inputStream.readUTF();
            final byte dataType = inputStream.readByte();
            if (dataType == Amf0TypeDef.EOO) {
                break;
            }
            amfObject.put(key, readData(dataType, inputStream));
        }
    }
}