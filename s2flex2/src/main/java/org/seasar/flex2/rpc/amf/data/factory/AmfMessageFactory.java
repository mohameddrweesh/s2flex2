package org.seasar.flex2.rpc.amf.data.factory;

import org.seasar.flex2.rpc.amf.data.AmfMessage;

public interface AmfMessageFactory {
    AmfMessage createMessage(int version);
}
