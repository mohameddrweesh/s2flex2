package org.seasar.flex2.core.format.amf.io.writer;

import java.io.DataOutputStream;
import java.io.IOException;

public interface AmfDataWriter {

    boolean isWritableValue(Object value);

    void writeAmfData(Object value, DataOutputStream outputStream) throws IOException;

}