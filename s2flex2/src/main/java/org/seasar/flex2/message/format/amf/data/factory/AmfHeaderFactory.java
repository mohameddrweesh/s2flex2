package org.seasar.flex2.message.format.amf.data.factory;

import org.seasar.flex2.message.format.amf.data.AmfHeader;

public interface AmfHeaderFactory {
    AmfHeader createHeader(String name, String value);
}
