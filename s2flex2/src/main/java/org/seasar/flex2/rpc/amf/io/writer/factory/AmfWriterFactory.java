package org.seasar.flex2.rpc.amf.io.writer.factory;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;

public interface AmfWriterFactory {

    AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message);

}