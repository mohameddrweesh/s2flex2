package org.seasar.flex2.rpc.amf.io.external.factory.impl;

import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.io.external.DataOutput;
import org.seasar.flex2.rpc.amf.io.external.ExternalizeDataOutput;
import org.seasar.flex2.rpc.amf.io.external.factory.DataOutputFactory;
import org.seasar.framework.container.S2Container;

public class ExternalizeDataOutputFactoryImpl implements DataOutputFactory {

    private S2Container container;
    
    public DataOutput createDataOutput(DataOutputStream outputStream) {
        ExternalizeDataOutput output = (ExternalizeDataOutput)container.getComponent(ExternalizeDataOutput.class);
        output.setOutputStream(outputStream);
        return output;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }
}
