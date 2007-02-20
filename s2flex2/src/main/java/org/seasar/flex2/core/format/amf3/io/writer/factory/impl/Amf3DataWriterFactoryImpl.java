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
package org.seasar.flex2.core.format.amf3.io.writer.factory.impl;

import org.seasar.flex2.core.format.amf.io.AmfDataWriter;
import org.seasar.flex2.core.format.amf0.io.writer.Amf0DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.Amf3DataWriter;
import org.seasar.flex2.core.format.amf3.io.writer.factory.Amf3DataWriterFactory;

public class Amf3DataWriterFactoryImpl implements Amf3DataWriterFactory {

    private Amf0DataWriter[] amf0DataWriters;

    private Amf3DataWriter[] amf3DataWriters;

    public Amf3DataWriter createAmf3DataWriter(final Object value) {
        final Amf3DataWriter writer = getAmf3DataWriter(value);
        if (writer == null) {
            throw new RuntimeException("Not Found Amf3Data Writer for "
                    + value.getClass());
        }
        return writer;
    }

    public AmfDataWriter createDataWriter(final Object value) {
        AmfDataWriter writer = getAmf0DataWriter(value);
        if (writer == null) {
            writer = getAmf3DataWriter(value);
        }
        if (writer == null) {
            throw new RuntimeException("Not Found Data Writer for "
                    + value.getClass());
        }
        return writer;
    }

    public void setAmf3DataWriters(final Amf3DataWriter[] amf3DataWriters) {
        this.amf3DataWriters = amf3DataWriters;
    }

    public void setWriterMap(final Amf0DataWriter[] amf0DataWriters) {
        this.amf0DataWriters = amf0DataWriters;
    }

    private final Amf0DataWriter getAmf0DataWriter(final Object value) {
        Amf0DataWriter targetWriter = null;
        for (int i = 0; i < amf0DataWriters.length; i++) {
            Amf0DataWriter writer = amf0DataWriters[i];
            if (writer.isWritableValue(value)) {
                targetWriter = writer;
                break;
            }
        }
        return targetWriter;
    }

    private final Amf3DataWriter getAmf3DataWriter(final Object value) {
        Amf3DataWriter targetWriter = null;
        for (int i = 0; i < amf3DataWriters.length; i++) {
            Amf3DataWriter writer = amf3DataWriters[i];
            if (writer.isWritableValue(value)) {
                targetWriter = writer;
                break;
            }
        }
        return targetWriter;
    }
}