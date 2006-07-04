package org.seasar.flex2.rpc.remoting.message.data.impl;

import org.seasar.flex2.rpc.remoting.message.data.MessageHeader;

public class MessageHeaderImpl implements MessageHeader {

    private final Object value;

    private int length = -1;

    private final String name;

    private boolean required = false;

    public MessageHeaderImpl(String name, Object data) {
        this.name = name;
        this.value = data;
    }

    public Object getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}