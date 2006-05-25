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
package org.seasar.flex2.rpc.amf.io.reader.data.impl.amf;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmfArrayReaderImpl extends AbstractAmfObjectReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readArray(inputStream);
    }

    protected List readArray(final DataInputStream inputStream)
            throws IOException {
        ArrayList array = new ArrayList();
        addSharedObject(array);
        int length = inputStream.readInt();
        for (int i = 0; i < length; i++) {
            byte dataType = inputStream.readByte();
            array.add(readData(dataType, inputStream));
        }
        return array;
    }
}