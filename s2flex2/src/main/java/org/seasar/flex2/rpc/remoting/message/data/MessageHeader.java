package org.seasar.flex2.rpc.remoting.message.data;

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

    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}