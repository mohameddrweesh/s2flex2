package org.seasar.flex2.message.format.amf.data;

public interface AmfHeader {
    String getName();

    boolean isRequired();

    int getLength();

    Object getData();
}
