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
import java.util.Date;
import java.util.TimeZone;

import org.seasar.flex2.core.format.amf0.Amf0Constants;
import org.seasar.flex2.core.format.amf0.io.reader.Amf0DataReader;

public class Amf0DateReaderImpl implements Amf0DataReader {

    private static final Date createDate(final double ms, final int offset) {
        final int defaultOffset = TimeZone.getDefault().getRawOffset();
        return new Date((long) (ms + defaultOffset - offset));
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readDate(inputStream);
    }

    private final Date readDate(final DataInputStream inputStream)
            throws IOException {
        final double ms = inputStream.readDouble();
        final int offset = inputStream.readUnsignedShort()
                * Amf0Constants.MILLS_PER_HOUR;
        return createDate(ms, offset);
    }
}