package org.seasar.flex2.rpc.amf.data.factory;

import org.seasar.flex2.rpc.amf.data.AmfHeader;

public interface AmfHeaderFactory {
    AmfHeader createHeader(String name, String value);
}
