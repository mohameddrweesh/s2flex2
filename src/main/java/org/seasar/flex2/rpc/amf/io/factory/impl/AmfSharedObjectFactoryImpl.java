package org.seasar.flex2.rpc.amf.io.factory.impl;

import org.seasar.flex2.rpc.amf.io.AmfSharedObject;
import org.seasar.flex2.rpc.amf.io.factory.AmfSharedObjectFactory;
import org.seasar.framework.container.S2Container;

public class AmfSharedObjectFactoryImpl implements AmfSharedObjectFactory {

    private S2Container container;

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public AmfSharedObject createSharedObject() {
        return (AmfSharedObject) container.getComponent(AmfSharedObject.class);
    }
}
