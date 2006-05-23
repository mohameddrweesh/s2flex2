package org.seasar.flex2.rpc.amf.data;

public interface AmfHeader {
    String getName();

    boolean isRequired();

    int getLength();

    Object getData();
}
