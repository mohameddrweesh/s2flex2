package org.seasar.flex2.rpc.amf.io.writer.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfSharedObject;
import org.seasar.flex2.rpc.amf.io.factory.AmfSharedObjectFactory;
import org.seasar.flex2.rpc.amf.io.writer.AmfWriter;
import org.seasar.flex2.rpc.amf.io.writer.factory.AmfWriterFactory;
import org.seasar.flex2.rpc.amf.io.writer.impl.AmfWriterImpl;
import org.seasar.framework.container.S2Container;

public class AmfWriterFactoryImpl implements AmfWriterFactory {

    private S2Container container;

    private AmfSharedObjectFactory sharedObjectFactory;

    public AmfWriter createWriter(final DataOutputStream dataOutputStream,
            final AmfMessage message) {
        AmfWriterImpl writer = (AmfWriterImpl) container
                .getComponent(AmfWriterImpl.class);
        writer.config(message, dataOutputStream);
        writer.setSharedObject(sharedObjectFactory.createSharedObject());
        return writer;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public void setSharedObjectFactory(AmfSharedObjectFactory sharedObjectFactory) {
        this.sharedObjectFactory = sharedObjectFactory;
    }
}
