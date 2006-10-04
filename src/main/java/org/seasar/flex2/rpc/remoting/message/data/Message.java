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
package org.seasar.flex2.rpc.remoting.message.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private List bodies;

    private Map headerMap;

    private List headers;

    private int version;

    public Message() {
        bodies = new ArrayList(4);
        headers = new ArrayList(8);
        headerMap = new HashMap(8);
        version = 0x03;
    }

    public void addBody(final MessageBody body) {
        bodies.add(body);
    }

    public void addHeader(final MessageHeader header) {
        headers.add(header);
        headerMap.put(header.getName(), header.getValue());
    }

    public MessageBody getBody(final int index) {
        return (MessageBody) bodies.get(index);
    }

    public int getBodySize() {
        return bodies.size();
    }

    public MessageHeader getHeader(final int index) {
        return (MessageHeader) headers.get(index);
    }

    public String getHeader(final String headerName) {
        return (String) headerMap.get(headerName);
    }

    public int getHeaderSize() {
        return headers.size();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }
}