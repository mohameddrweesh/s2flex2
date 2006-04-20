package org.seasar.flex2.rpc.amf.io.factory;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfWriter;


public interface AmfWriterFactory {

    AmfWriter createWriter( final DataOutputStream dataOutputStream, final AmfMessage message);

}