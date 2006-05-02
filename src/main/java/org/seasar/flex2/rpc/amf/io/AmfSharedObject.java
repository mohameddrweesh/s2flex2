package org.seasar.flex2.rpc.amf.io;


public interface AmfSharedObject {

    void addSharedObject(Object value);

    int getSharedIndex(Object value);

    void clean();
}