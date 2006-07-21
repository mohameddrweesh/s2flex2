/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar2.flex2.rpc.remoting.service.browser.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.container.ComponentDef;
import org.seasar2.flex2.rpc.remoting.service.browser.ServiceDetail;
import org.seasar2.flex2.rpc.remoting.service.browser.ServiceMethodDetail;
import org.seasar2.flex2.rpc.remoting.service.browser.service.BrowserService;

public class BrowserServiceImpl implements BrowserService {

    private static final String[] createClassNames(final Class[] classes) {
        String[] classNames = new String[classes.length];

        for (int i = 0; i < classes.length; i++) {
            classNames[i] = classes[i].getName();
        }

        return classNames;
    }

    private static final ServiceMethodDetail createMethodDetail(
            final Method method) {
        ServiceMethodDetail methodDetail = new ServiceMethodDetail();
        methodDetail.setName(method.getName());
        methodDetail.setArguments(createClassNames(method.getParameterTypes()));
        methodDetail.setReturnType(method.getReturnType().getName());

        return methodDetail;
    }

    private static final List createMethodDetails(final Class[] interfaces) {
        ArrayList list = new ArrayList();

        for (int i = 0; i < interfaces.length; i++) {
            Class interface_ = interfaces[i];
            Method[] methods = interface_.getDeclaredMethods();
            for (int j = 0; j < methods.length; j++) {
                list.add(createMethodDetail(methods[j]));
            }
        }

        return list;
    }

    private static final ServiceDetail createServiceDetail(final String name,
            final ComponentDef def) {
        ServiceDetail detail = new ServiceDetail();
        detail.setName(name);

        Class clazz = def.getComponentClass();
        detail.setClassName(clazz.getName());
        detail.setInterfaces(createClassNames(clazz.getInterfaces()));

        detail.setMethodDetails(createMethodDetails(clazz.getInterfaces()));

        return detail;
    }

    private RemotingServiceRepository repository;

    public ServiceDetail getServiceDetail(final String serviceName) {
        return createServiceDetail(serviceName, repository
                .getService(serviceName));
    }

    private static final String BROWSE_SERVICE = "browserService";

    public String[] getServiceNames() {
        Set serviceNames = repository.getServiceNames();
        int serviceNumber = serviceNames.size() > 0 ? serviceNames.size() - 1
                : 0;
        String[] serviceNameArray = new String[serviceNumber];

        if (serviceNumber > 0) {
            int i = 0;
            for (Iterator serviceNameIter = serviceNames.iterator(); serviceNameIter
                    .hasNext();) {
                String serviceName = (String) serviceNameIter.next();
                if (BROWSE_SERVICE.equals(serviceName)) {
                    continue;
                }
                serviceNameArray[i++] = serviceName;
            }
        }

        return serviceNameArray;
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }
}
