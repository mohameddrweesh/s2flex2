package org.seasar.flex2.message.format.amf.service;

import org.seasar.flex2.message.format.amf.data.AmfBody;

public interface ServiceInvokerChooser {
    ServiceInvoker chooseInvoker(AmfBody requestBody);
}
