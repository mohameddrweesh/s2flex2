package org.seasar.flex2.message.format.amf.data.factory.impl;

import org.seasar.flex2.message.format.amf.data.AmfError;
import org.seasar.flex2.message.format.amf.data.factory.AmfErrorFactory;
import org.seasar.flex2.message.format.amf.data.impl.AmfErrorImpl;

public class AmfErrorFactoryImpl implements AmfErrorFactory {

    public AmfError createError(Throwable throwable) {
        return new AmfErrorImpl(throwable);
    }

}
