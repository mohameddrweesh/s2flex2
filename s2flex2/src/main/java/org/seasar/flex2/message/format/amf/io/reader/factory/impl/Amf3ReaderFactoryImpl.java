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
package org.seasar.flex2.message.format.amf.io.reader.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.message.format.amf.io.factory.AmfSharedObjectFactory;
import org.seasar.flex2.message.format.amf.io.reader.AmfReader;
import org.seasar.flex2.message.format.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.message.format.amf.io.reader.impl.amf3.Amf3ReaderImpl;
import org.seasar.framework.container.S2Container;

public class Amf3ReaderFactoryImpl implements AmfReaderFactory {

    private S2Container container;

    private AmfSharedObjectFactory sharedObjectFactory;

    public AmfReader createReader(final DataInputStream dataInputStream) {
        Amf3ReaderImpl reader = (Amf3ReaderImpl) container
                .getComponent(Amf3ReaderImpl.class);
        reader.config(dataInputStream);
        reader.setSharedObject(sharedObjectFactory.createSharedObject());
        return reader;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void setSharedObjectFactory(
            AmfSharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }
}
