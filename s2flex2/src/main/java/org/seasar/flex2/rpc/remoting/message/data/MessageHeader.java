/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
/**
 * AMFのMessageヘッダクラスです。
 * @author e1.arkw
 * @author nod
 * {@link Message}のヘッダ部になります
 */
public class MessageHeader {

    private int length = -1;

    private String name;

    private boolean required;

    private Object value;

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }

    public void setLength(final int length) {
        this.length = length;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}