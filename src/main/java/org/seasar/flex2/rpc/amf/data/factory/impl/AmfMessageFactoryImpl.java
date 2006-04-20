package org.seasar.flex2.rpc.amf.data.factory.impl;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.data.factory.AmfMessageFactory;
import org.seasar.flex2.rpc.amf.data.impl.AmfMessageImpl;

public class AmfMessageFactoryImpl implements AmfMessageFactory {

    public AmfMessage createMessage( int version ) {
        AmfMessageImpl responseMessage = new AmfMessageImpl();
        responseMessage.setVersion(version);

        return responseMessage;
    }

}
