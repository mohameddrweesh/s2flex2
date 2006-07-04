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
package org.seasar.flex2.rpc.remoting.message.io.reader.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.remoting.message.io.reader.MessageReader;
import org.seasar.flex2.rpc.remoting.message.io.reader.factory.MessageReaderFactory;
import org.seasar.flex2.rpc.remoting.message.io.reader.impl.AmfMessageReaderImpl;
import org.seasar.framework.container.S2Container;

public class MessageReaderFactoryImpl implements MessageReaderFactory {

    private Class messageReaderClass;

    private S2Container container;

    public MessageReader createMessageReader(
            final DataInputStream dataInputStream) {
        AmfMessageReaderImpl reader = (AmfMessageReaderImpl) container
                .getComponent(messageReaderClass);
        reader.config(dataInputStream);

        return reader;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void setMessageReaderClass(Class amfMessageReaderClass) {
        this.messageReaderClass = amfMessageReaderClass;
    }
}
