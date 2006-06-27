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
package org.seasar.flex2.message.format.amf.data.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.flex2.message.format.amf.data.AmfBody;
import org.seasar.flex2.message.format.amf.data.AmfHeader;
import org.seasar.flex2.message.format.amf.data.AmfMessage;

public class AmfMessageImpl implements AmfMessage {

    private List bodies = new ArrayList();

    private List headers = new ArrayList();

    private int version = 0x03;

    public AmfMessageImpl() {
    }

    public void addBody(AmfBody body) {
        bodies.add(body);
    }

    public AmfBody getBody(int index) {
        return (AmfBody) bodies.get(index);
    }

    public int getBodySize() {
        return bodies.size();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void addHeader(AmfHeader body) {
        headers.add(body);
    }

    public AmfHeader getHeader(int index) {
        return (AmfHeader) headers.get(index);
    }

    public int getHeaderSize() {
        return headers.size();
    }
}