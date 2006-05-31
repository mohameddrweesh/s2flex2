package org.seasar.flex2.message.format.amf.data.factory;

import org.seasar.flex2.message.format.amf.data.AmfError;

public interface AmfErrorFactory {
    AmfError createError(Throwable throwable);
}
