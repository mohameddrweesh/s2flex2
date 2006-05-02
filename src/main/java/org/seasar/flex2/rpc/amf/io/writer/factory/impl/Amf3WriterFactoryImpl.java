package org.seasar.flex2.rpc.amf.io.writer.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.factory.Amf3ReferencesFactory;
import org.seasar.flex2.rpc.amf.io.factory.AmfSharedObjectFactory;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.writer.impl.Amf3WriterImpl;
import org.seasar.framework.container.S2Container;

public class Amf3WriterFactoryImpl implements AmfWriterFactory {

    private S2Container container;

    private Amf3ReferencesFactory referencesFactory;

    private AmfSharedObjectFactory sharedObjectFactory;

    public AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message) {
        Amf3WriterImpl writer = (Amf3WriterImpl) container
                .getComponent(Amf3WriterImpl.class);
        writer.config(message, dataOutputStream);
        writer.setSharedObject(sharedObjectFactory.createSharedObject());
        writer.setReferences(referencesFactory.createReferences());
        return writer;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void setReferencesFactory(Amf3ReferencesFactory referencesFactory) {
        this.referencesFactory = referencesFactory;
    }

    public void setSharedObjectFactory(
            AmfSharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }
}
