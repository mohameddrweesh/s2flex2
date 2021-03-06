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
package org.seasar.flex2.rpc.remoting.message.data.factory.impl;

import org.seasar.flex2.rpc.remoting.message.data.MessageHeader;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageHeaderFactory;
import org.seasar.framework.container.S2Container;

public class MessageHeaderFactoryImpl implements MessageHeaderFactory {

    private S2Container container;

    public MessageHeader createHeader(final String name, final Object value,
            final boolean required) {
        final MessageHeader header = (MessageHeader) container
                .getComponent(MessageHeader.class);
        header.setName(name);
        header.setValue(value);
        header.setRequired(required);

        return header;
    }

    public void setContainer(final S2Container container) {
        this.container = container;
    }

}
