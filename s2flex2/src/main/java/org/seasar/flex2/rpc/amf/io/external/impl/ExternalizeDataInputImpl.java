package org.seasar.flex2.rpc.amf.io.external.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.io.external.ExternalizeDataInput;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.flex2.rpc.amf.io.reader.data.factory.Amf3DataReaderFactory;

public class ExternalizeDataInputImpl implements ExternalizeDataInput {

    private DataInputStream inputStream;

    private Amf3DataReaderFactory readerFactory;

    public ExternalizeDataInputImpl() {
    }

    public Object readObject() throws IOException {
        byte dataType = inputStream.readByte();
        AmfDataReader dataReader = readerFactory.createAmf3DataReader(dataType);
        return dataReader.read(inputStream);
    }

    public void setInputStream(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void setReaderFactory(Amf3DataReaderFactory readerFactory) {
        this.readerFactory = readerFactory;
    }
}
