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
package org.seasar.flex2.message.format.amf.io.reader.impl.amf;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.message.format.amf.AmfConstants;
import org.seasar.flex2.message.format.amf.type.AmfTypeDef;

import flashgateway.io.ASObject;

public class AmfObjectReaderImpl extends AbstractAmfClassObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Object readObject(final DataInputStream inputStream)
            throws IOException {
        ASObject asObject = new ASObject();
        addSharedObject(asObject);

        readProperties(inputStream, asObject);

        Object result = null;
        if (asObject.containsKey(AmfConstants.REMOTE_CLASS)) {
            result = translateBean(asObject);
        } else {
            result = asObject;
        }

        return result;
    }

    private final void readProperties(final DataInputStream inputStream,
            ASObject asObject) throws IOException {
        while (true) {
            String key = inputStream.readUTF();
            byte dataType = inputStream.readByte();
            if (dataType == AmfTypeDef.EOM) {
                break;
            }
            asObject.put(key, readData(dataType, inputStream));
        }
    }
}