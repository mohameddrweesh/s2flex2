package org.seasar2.flex2.rpc.remoting.service.browser.service;

import org.seasar2.flex2.rpc.remoting.service.browser.ServiceDetail;

public interface BrowserService {
    String[] getServiceNames();

    ServiceDetail getServiceDetail(String serviceName);
}
