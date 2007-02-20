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
package org.seasar.flex2.core.format.amf0.io.writer.factory.impl;

import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf0.io.writer.factory.Amf0DataWriterFactory;

public class Amf0DataWriterFactoryImpl implements Amf0DataWriterFactory {

    protected Amf0DataWriter[] amf0DataWriters;

    public AmfDataWriter createDataWriter(final Object value) {
        final AmfDataWriter writer = getAmf0DataWriter(value);
        if (writer == null) {
            throw new RuntimeException("Not Found Amf0 Data Writer for "
                    + value.getClass());
        }
        return writer;
    }

    public void setAmf0DataWriters(final Amf0DataWriter[] amf0DataWriters) {
        this.amf0DataWriters = amf0DataWriters;
    }

    private final Amf0DataWriter getAmf0DataWriter(final Object value) {
        Amf0DataWriter targetWriter = null;
        for (int i = 0; i < amf0DataWriters.length; i++) {
            if (amf0DataWriters[i].isWritableValue(value)) {
                targetWriter = amf0DataWriters[i];
                break;
            }
        }
        return targetWriter;
    }
}