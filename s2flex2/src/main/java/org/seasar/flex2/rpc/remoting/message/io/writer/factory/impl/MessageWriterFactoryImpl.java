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
package org.seasar.flex2.rpc.remoting.message.io.writer.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.io.writer.MessageWriter;
import org.seasar.flex2.rpc.remoting.message.io.writer.factory.MessageWriterFactory;
import org.seasar.flex2.rpc.remoting.message.io.writer.impl.AmfMessageWriterImpl;
import org.seasar.framework.container.S2Container;

public class MessageWriterFactoryImpl implements MessageWriterFactory {

    private S2Container container;

    private Class messageWriterClass;

    public MessageWriter createMessageWriter(
            final DataOutputStream dataOutputStream, final Message message) {
        final AmfMessageWriterImpl writer = (AmfMessageWriterImpl) container
                .getComponent(messageWriterClass);
        writer.config(message, dataOutputStream);
        return writer;
    }

    public void setContainer(final S2Container container) {
        this.container = container;
    }

    public void setMessageWriterClass(final Class amfMessageWriterClass) {
        this.messageWriterClass = amfMessageWriterClass;
    }
}
