package org.seasar.flex2.rpc.amf.io.writer.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.Amf3References;
import org.seasar.flex2.rpc.amf.io.AmfSharedObject;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.writer.impl.Amf3WriterImpl;
import org.seasar.framework.container.S2Container;

public class Amf3WriterFactoryImpl implements AmfWriterFactory {

    private S2Container container;

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message) {
        Amf3WriterImpl writer = (Amf3WriterImpl) container.getComponent(Amf3WriterImpl.class);
        writer.config(message, dataOutputStream);
        writer.setSharedObject((AmfSharedObject)container.getComponent(AmfSharedObject.class));
        writer.setReferences((Amf3References)container.getComponent(Amf3References.class));
        return writer;
    }
}
