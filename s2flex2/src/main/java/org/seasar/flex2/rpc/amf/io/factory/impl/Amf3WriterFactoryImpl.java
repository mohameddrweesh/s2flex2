package org.seasar.flex2.rpc.amf.io.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.io.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.impl.Amf3WriterImpl;

public class Amf3WriterFactoryImpl implements AmfWriterFactory {

    public AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message){
        return new Amf3WriterImpl( dataOutputStream, message ); 
    }
}
