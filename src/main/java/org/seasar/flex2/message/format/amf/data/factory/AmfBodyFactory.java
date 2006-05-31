package org.seasar.flex2.message.format.amf.data.factory;

import org.seasar.flex2.message.format.amf.data.AmfBody;

public interface AmfBodyFactory {
    AmfBody createBody(String target, String response, Object data);
}
