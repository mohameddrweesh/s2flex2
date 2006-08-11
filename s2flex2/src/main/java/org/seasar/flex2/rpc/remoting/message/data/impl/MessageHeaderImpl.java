package org.seasar.flex2.rpc.remoting.message.data.impl;

import org.seasar.flex2.rpc.remoting.message.data.MessageHeader;

public class MessageHeaderImpl implements MessageHeader {

    private int length = -1;

    private final String name;

    private boolean required;

    private final Object value;

    public MessageHeaderImpl(String name, Object data, boolean required) {
        this.name = name;
        this.value = data;
        this.required = required;
    }

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

    public void setLength(int length) {
        this.length = length;
    }
}