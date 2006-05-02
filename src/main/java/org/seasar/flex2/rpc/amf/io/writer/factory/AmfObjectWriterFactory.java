package org.seasar.flex2.rpc.amf.io.writer.factory;

import org.seasar.flex2.rpc.amf.io.writer.data.AmfObjectWriter;

public interface AmfObjectWriterFactory {

    AmfObjectWriter createObjectWriter(Object value);

}