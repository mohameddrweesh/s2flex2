package org.seasar.flex2.rpc.amf.io.external.factory;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.io.external.DataOutput;

public interface DataOutputFactory {
    DataOutput createDataOutput(DataOutputStream outputStream);
}
