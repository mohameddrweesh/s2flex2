package org.seasar.flex2.rpc.amf.io.reader.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.reader.AmfReader;
import org.seasar.flex2.rpc.amf.io.reader.factory.AmfReaderFactory;
import org.seasar.flex2.rpc.amf.io.reader.impl.Amf3ReaderImpl;

public class Amf3ReaderFactoryImpl implements AmfReaderFactory {

    public AmfReader createReader(final DataInputStream dataInputStream) {
        return new Amf3ReaderImpl(dataInputStream);
    }
}
