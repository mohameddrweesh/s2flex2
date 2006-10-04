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
package org.seasar.flex2.core.format.amf.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.AmfConstants;
import org.seasar.flex2.core.format.amf.type.AmfObject;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;

public class AmfObjectReaderImpl extends AbstractAmfClassObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Object readCustomClass(final AmfObject amfObject) {
        Object result = amfObject;
        if (amfObject.containsKey(AmfConstants.REMOTE_CLASS)) {
            result = translateBean(amfObject);
        }
        return result;
    }

    private final Object readObject(final DataInputStream inputStream)
            throws IOException {
        final AmfObject amfObject = new AmfObject();
        addSharedObject(amfObject);

        readProperties(inputStream, amfObject);

        return readCustomClass(amfObject);
    }

    private final void readProperties(final DataInputStream inputStream,
            final AmfObject amfObject) throws IOException {
        while (true) {
            final String key = inputStream.readUTF();
            final byte dataType = inputStream.readByte();
            if (dataType == AmfTypeDef.EOM) {
                break;
            }
            amfObject.put(key, readData(dataType, inputStream));
        }
    }
}