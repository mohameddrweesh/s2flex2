package org.seasar.flex2.rpc.amf.io.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.io.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.impl.AmfWriterImpl;

public class AmfWriterFactoryImpl implements AmfWriterFactory {

    public AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message){
        return new AmfWriterImpl( dataOutputStream, message ); 
    }
}
