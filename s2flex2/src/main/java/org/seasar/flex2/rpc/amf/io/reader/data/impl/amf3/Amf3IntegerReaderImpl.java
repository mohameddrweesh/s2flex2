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
package org.seasar.flex2.rpc.amf.io.reader.data.impl.amf3;

import java.io.DataInputStream;
import java.io.IOException;

public class Amf3IntegerReaderImpl extends AbstractAmf3IntReaderImpl {

    public Object read(final DataInputStream inputStream) throws IOException {
        return readInteger(inputStream);
    }

    private final Integer readInteger(final DataInputStream inputStream)
            throws IOException {
        Integer result = new Integer(readInt(inputStream));

        logger.debug("<amf3> readInteger:" + result);

        return result;
    }
}