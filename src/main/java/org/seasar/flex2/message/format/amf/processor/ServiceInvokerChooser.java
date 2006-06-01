package org.seasar.flex2.message.format.amf.processor;

import org.seasar.flex2.message.format.amf.data.AmfBody;
import org.seasar.flex2.message.format.amf.service.ServiceInvoker;

public interface ServiceInvokerChooser {
    ServiceInvoker chooseInvoker(AmfBody requestBody);
}
