package org.seasar.flex2.rpc.amf.io.reader.factory;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.reader.AmfReader;

public interface AmfReaderFactory {

    AmfReader createReader(DataInputStream dataInputStream);

}