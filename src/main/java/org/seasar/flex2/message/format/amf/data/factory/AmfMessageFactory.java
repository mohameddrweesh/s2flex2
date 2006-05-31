package org.seasar.flex2.message.format.amf.data.factory;

import org.seasar.flex2.message.format.amf.data.AmfMessage;

public interface AmfMessageFactory {
    AmfMessage createMessage(int version);
}
