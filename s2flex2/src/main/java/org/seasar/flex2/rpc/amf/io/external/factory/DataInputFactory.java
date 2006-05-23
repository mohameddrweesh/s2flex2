package org.seasar.flex2.rpc.amf.io.external.factory;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.external.DataInput;

public interface DataInputFactory {
    DataInput createDataIpput( DataInputStream inputStream );
}
