package org.seasar.flex2.rpc.amf.data.factory.impl;

import org.seasar.flex2.rpc.amf.data.AmfHeader;
import org.seasar.flex2.rpc.amf.data.factory.AmfHeaderFactory;
import org.seasar.flex2.rpc.amf.data.impl.AmfHeaderImpl;

public class AmfHeaderFactoryImpl implements AmfHeaderFactory {

    public AmfHeader createHeader(String name, String value) {
        return new AmfHeaderImpl(name, value);
    }

}
