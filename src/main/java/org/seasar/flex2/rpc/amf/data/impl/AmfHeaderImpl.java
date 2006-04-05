package org.seasar.flex2.rpc.amf.data.impl;

import org.seasar.flex2.rpc.amf.data.AmfHeader;

public class AmfHeaderImpl implements AmfHeader {

    private final Object data;

    private int length = -1;

    private final String name;

    private boolean required = false;

    public AmfHeaderImpl(String name, Object data) {
        this.name = name;
        this.data = data;
    }

    public Object getData() {
        return data;
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