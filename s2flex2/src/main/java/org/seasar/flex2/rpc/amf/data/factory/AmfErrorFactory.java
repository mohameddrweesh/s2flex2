package org.seasar.flex2.rpc.amf.data.factory;

import org.seasar.flex2.rpc.amf.data.AmfError;

public interface AmfErrorFactory {
    AmfError createError(Throwable throwable);
}
