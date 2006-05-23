package org.seasar.flex2.rpc.amf.io.external.factory.impl;

import java.io.DataInputStream;

import org.seasar.flex2.rpc.amf.io.external.DataInput;
import org.seasar.flex2.rpc.amf.io.external.ExternalizeDataInput;
import org.seasar.flex2.rpc.amf.io.external.factory.DataInputFactory;
import org.seasar.framework.container.S2Container;

public class ExternalizeDataInputFactoryImpl implements DataInputFactory {

    private S2Container container;
    
    public DataInput createDataIpput(DataInputStream inputStream) {
        ExternalizeDataInput input = (ExternalizeDataInput)container.getComponent(ExternalizeDataInput.class);
        input.setInputStream(inputStream);
        return input;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

}
