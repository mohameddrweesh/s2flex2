package org.seasar.flex2.rpc.amf.io.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.AmfReader;
import org.seasar.flex2.rpc.amf.io.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.impl.AmfReaderImpl;

public class AmfReaderFactoryImpl implements AmfReaderFactory {

    public AmfReader createReader( final DataInputStream dataInputStream){
        return new AmfReaderImpl( dataInputStream ); 
    }
}
