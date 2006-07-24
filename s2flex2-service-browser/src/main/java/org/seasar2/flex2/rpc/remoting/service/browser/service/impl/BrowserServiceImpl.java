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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceRepository;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar2.flex2.rpc.remoting.service.browser.ServiceDetail;
import org.seasar2.flex2.rpc.remoting.service.browser.ServiceMethodDetail;
import org.seasar2.flex2.rpc.remoting.service.browser.service.BrowserService;

public class BrowserServiceImpl implements BrowserService {

    private static final String ARGUMENTS_DELIMITER = " ";

    private static final String BROWSE_SERVICE = "browserService";

    private static final String[] convertClassToString(final Class[] classes) {
        final String[] classNames = new String[classes.length];

        for (int i = 0; i < classes.length; i++) {
            classNames[i] = classes[i].getName();
        }

        return classNames;
    }

    private static final List createMethodArgumentDetails(
            final String[] argumentTyes, final String[] paramNames) {
        List arguments = null;
        if (paramNames != null && paramNames.length == argumentTyes.length) {
            arguments = new ArrayList();
            for (int i = 0; i < argumentTyes.length; i++) {
                arguments.add(formatArgumentDetail(argumentTyes[i],
                        paramNames[i]));
            }
        } else {
            arguments = Arrays.asList(argumentTyes);
        }

        return arguments;
    }

    private static String formatArgumentDetail(String type, String name) {
        return type + ARGUMENTS_DELIMITER + name;
    }

    private static final ServiceMethodDetail createMethodDetail(
            final Method method, final String[] paramNames) {
        final ServiceMethodDetail methodDetail = new ServiceMethodDetail();
        methodDetail.setName(method.getName());

        final String[] argumentTyes = convertClassToString(method
                .getParameterTypes());

        if (argumentTyes.length > 0) {
            methodDetail.setArguments(createMethodArgumentDetails(argumentTyes,
                    paramNames));
        }
        methodDetail.setReturnType(method.getReturnType().getName());

        return methodDetail;
    }

    private static final List createMethodDetails(final Class[] interfaces) {
        final ArrayList list = new ArrayList();

        for (int i = 0; i < interfaces.length; i++) {
            createMethodDetailsBy(interfaces[i], list);
        }

        return list;
    }

    private static final void createMethodDetailsBy(final Class interfaceClass,
            final ArrayList list) {
        final BeanDesc deanDesc = BeanDescFactory.getBeanDesc(interfaceClass);
        final Method[] methods = interfaceClass.getDeclaredMethods();

        for (int j = 0; j < methods.length; j++) {
            list.add(createMethodDetail(methods[j], deanDesc
                    .getMethodParameterNames(methods[j])));
        }
    }

    private static final void createServiceClassDetail(final Class clazz,
            final ServiceDetail detail) {
        detail.setClassName(clazz.getName());
        detail.setInterfaces(convertClassToString(clazz.getInterfaces()));
        detail.setMethodDetails(createMethodDetails(clazz.getInterfaces()));
    }

    private static final ServiceDetail createServiceDetail(final String name,
            final ComponentDef def) {
        final ServiceDetail detail = new ServiceDetail();

        detail.setName(name);
        createServiceClassDetail(def.getComponentClass(), detail);

        return detail;
    }

    private RemotingServiceRepository repository;

    public ServiceDetail getServiceDetail(final String serviceName) {
        return createServiceDetail(serviceName, repository
                .getService(serviceName));
    }

    public String[] getServiceNames() {
        Set serviceNames = repository.getServiceNames();
        int serviceNumber = serviceNames.size() > 0 ? serviceNames.size() - 1
                : 0;
        String[] serviceNameArray = new String[serviceNumber];

        if (serviceNameArray.length > 0) {
            createServiceNames(serviceNames, serviceNameArray);
        }

        return serviceNameArray;
    }

    public void setRepository(RemotingServiceRepository repository) {
        this.repository = repository;
    }

    private final void createServiceNames(Set serviceNames,
            String[] serviceNameArray) {
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
}
