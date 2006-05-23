package org.seasar.flex2.rpc.amf.io.external.impl;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.io.external.ExternalizeDataOutput;
import org.seasar.flex2.rpc.amf.io.writer.data.Amf3DataWriter;
import org.seasar.flex2.rpc.amf.io.writer.data.factory.Amf3DataWriterFactory;

public class ExternalizeDataOutputImpl implements ExternalizeDataOutput {

    private DataOutputStream outputStream;

    private Amf3DataWriterFactory writerFactory;

    public ExternalizeDataOutputImpl() {
    }

    public void setOutputStream(DataOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setWriterFactory(Amf3DataWriterFactory writerFactory) {
        this.writerFactory = writerFactory;
    }

    public void writeObject(Object object) throws IOException {
        Amf3DataWriter dataWriter = writerFactory.createDataValueWriter(object);
        dataWriter.writeData(object, outputStream);
    }
}
