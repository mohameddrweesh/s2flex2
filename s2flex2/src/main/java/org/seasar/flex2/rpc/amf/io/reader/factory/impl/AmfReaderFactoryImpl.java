package org.seasar.flex2.rpc.amf.io.reader.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.reader.AmfReader;
import org.seasar.flex2.rpc.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.reader.impl.AmfReaderImpl;

public class AmfReaderFactoryImpl implements AmfReaderFactory {

    public AmfReader createReader(final DataInputStream dataInputStream) {
        return new AmfReaderImpl(dataInputStream);
    }
}
