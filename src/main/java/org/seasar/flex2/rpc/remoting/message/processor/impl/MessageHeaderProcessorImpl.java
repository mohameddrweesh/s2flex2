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
package org.seasar.flex2.rpc.remoting.message.processor.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.seasar.flex2.rpc.remoting.message.data.Message;
import org.seasar.flex2.rpc.remoting.message.data.MessageHeader;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageHeaderFactory;
import org.seasar.flex2.rpc.remoting.message.processor.MessageHeaderCreator;
import org.seasar.flex2.rpc.remoting.message.processor.MessageHeaderProcessor;

public class MessageHeaderProcessorImpl implements MessageHeaderProcessor {

    private final static void addHeader(final Message message,
            final MessageHeaderCreator creator) {
        final MessageHeader header = creator.createHeader(message);
        if (header != null) {
            message.addHeader(header);
        }
    }

    private final List headerCreators;

    private MessageHeaderFactory headerFactory;

    public MessageHeaderProcessorImpl() {
        headerCreators = new ArrayList();
    }

    public void addHeaderCreator(MessageHeaderCreator creator) {
        headerCreators.add(creator);
    }

    public MessageHeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    public void processRequest(final Message requestMessage) {
    }

    public void processResponse(final Message responseMessage) {
        createMessageHeaders(responseMessage);
    }

    public void setHeaderFactory(MessageHeaderFactory headerFactory) {
        this.headerFactory = headerFactory;
    }

    private final void createMessageHeaders(final Message message) {
        if (headerCreators.size() > 0) {
            for (Iterator creatorIt = headerCreators.iterator(); creatorIt
                    .hasNext();) {
                addHeader(message, (MessageHeaderCreator) creatorIt
                        .next());
            }
        }
    }
}