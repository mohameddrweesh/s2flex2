package org.seasar.flex2.rpc.amf.data.factory;

import org.seasar.flex2.rpc.amf.data.AmfBody;

public interface AmfBodyFactory {
    AmfBody createBody(String target, String response, Object data);
}
