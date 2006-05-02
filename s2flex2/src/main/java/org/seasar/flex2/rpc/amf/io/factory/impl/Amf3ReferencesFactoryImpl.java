package org.seasar.flex2.rpc.amf.io.factory.impl;

import org.seasar.flex2.rpc.amf.io.Amf3References;
import org.seasar.flex2.rpc.amf.io.factory.Amf3ReferencesFactory;
import org.seasar.framework.container.S2Container;

public class Amf3ReferencesFactoryImpl implements Amf3ReferencesFactory {

    private S2Container container;

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public Amf3References createReferences() {
        return (Amf3References) container.getComponent(Amf3References.class);
    }
}
