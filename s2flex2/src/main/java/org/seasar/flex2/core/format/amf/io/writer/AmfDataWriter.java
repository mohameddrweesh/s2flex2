package org.seasar.flex2.core.format.amf.io.writer;

import java.io.DataOutputStream;
import java.io.IOException;

public interface AmfDataWriter {
   
    void write(Object value, DataOutputStream outputStream) throws IOException;

}