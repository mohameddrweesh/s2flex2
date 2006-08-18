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

import org.seasar.flex2.rpc.remoting.message.data.MessageBody;
import org.seasar.flex2.rpc.remoting.message.data.factory.MessageBodyFactory;
import org.seasar.framework.container.S2Container;

public class MessageBodyFactoryImpl implements MessageBodyFactory {

    private S2Container container;

    private String defaultResponseTarget = "";

    public MessageBody createBody(String target, String response, Object data) {
        MessageBody body;
        if (response != null) {
            body = createMessageBody(target, response, data);
        } else {
            body = createMessageBody(target, getDefaultResponseTarget(), data);
        }
        return body;
    }

    public String getDefaultResponseTarget() {
        return defaultResponseTarget;
    }

    public void setDefaultResponseTarget(String responseTarget) {
        this.defaultResponseTarget = responseTarget;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    private final MessageBody createMessageBody(final String target,
            final String response, final Object data) {

        final MessageBody body = (MessageBody) container
                .getComponent(MessageBody.class);
        body.setTarget(target);
        body.setResponse(response);
        body.setData(data);

        return body;
    }
}
