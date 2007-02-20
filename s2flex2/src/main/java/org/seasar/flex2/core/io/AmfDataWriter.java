package org.seasar.flex2.core.io;

import java.io.DataOutputStream;
import java.io.IOException;

public interface AmfDataWriter {

    boolean isWritableValue(Object value);

    void write(Object value, DataOutputStream outputStream) throws IOException;

}