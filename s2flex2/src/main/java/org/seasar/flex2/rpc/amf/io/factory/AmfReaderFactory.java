package org.seasar.flex2.rpc.amf.io.factory;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.AmfReader;


public interface AmfReaderFactory {

    AmfReader createReader(DataInputStream dataInputStream);

}