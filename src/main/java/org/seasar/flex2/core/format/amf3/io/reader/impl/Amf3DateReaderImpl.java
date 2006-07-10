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
import java.util.Date;

public class Amf3DateReaderImpl extends AbstractAmf3ObjectReaderImpl {

    private static final Date getDate(final double ms) {
        return new Date((long) ms);
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    private final Date readDateData(final DataInputStream inputStream)
            throws IOException {
        final Date date = getDate(inputStream.readDouble());
        addObjectReference(date);
        return date;
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return readDateData(inputStream);
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }
}