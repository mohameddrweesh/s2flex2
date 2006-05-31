package org.seasar.flex2.message.format.amf.data.factory.impl;

import org.seasar.flex2.message.format.amf.data.AmfHeader;
import org.seasar.flex2.message.format.amf.data.factory.AmfHeaderFactory;
import org.seasar.flex2.message.format.amf.data.impl.AmfHeaderImpl;

public class AmfHeaderFactoryImpl implements AmfHeaderFactory {

    public AmfHeader createHeader(String name, String value) {
        return new AmfHeaderImpl(name, value);
    }

}
