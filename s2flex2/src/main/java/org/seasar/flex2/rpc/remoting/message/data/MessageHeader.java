package org.seasar.flex2.rpc.remoting.message.data;

public interface MessageHeader {
    String getName();

    boolean isRequired();

    int getLength();

    Object getValue();
}
