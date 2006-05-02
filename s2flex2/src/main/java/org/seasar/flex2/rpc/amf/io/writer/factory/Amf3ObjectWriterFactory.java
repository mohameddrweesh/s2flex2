package org.seasar.flex2.rpc.amf.io.writer.factory;

import org.seasar.flex2.rpc.amf.io.writer.data.Amf3ObjectWriter;

public interface Amf3ObjectWriterFactory extends AmfObjectWriterFactory{

    Amf3ObjectWriter createObjectDataWriter(Object value);

}