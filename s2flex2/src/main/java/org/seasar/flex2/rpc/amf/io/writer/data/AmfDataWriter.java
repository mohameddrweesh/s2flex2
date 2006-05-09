package org.seasar.flex2.rpc.amf.io.writer.data;

import java.io.DataOutputStream;
import java.io.IOException;

public interface AmfDataWriter {
   
    void write(Object value, DataOutputStream outputStream) throws IOException;

}