package org.seasar.flex2.rpc.remoting.service;

import org.seasar.flex2.rpc.remoting.message.data.MessageBody;

public interface RemotingServiceInvokerChooser {
    RemotingServiceInvoker chooseInvoker(MessageBody requestBody);
}
