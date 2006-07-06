package org.seasar.flex2.rpc.remoting.message.data;

public interface MessageHeader {
    int getLength();

    String getName();

    Object getValue();

    boolean isRequired();
}
