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
package org.seasar.flex2.rpc.remoting.service.impl;

import java.lang.reflect.Method;

import org.seasar.flex2.rpc.remoting.service.RemotingServiceInvoker;
import org.seasar.flex2.rpc.remoting.service.RemotingServiceLocator;
import org.seasar.flex2.rpc.remoting.service.adjustor.ArgumentAdjustor;
import org.seasar.flex2.rpc.remoting.service.exception.InvaildServiceArgumentException;
import org.seasar.flex2.rpc.remoting.service.validator.RemotingServiceValidator;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;

public abstract class AbstractRemotingServiceInvokerImpl implements
        RemotingServiceInvoker {

    private static final Logger logger = Logger
            .getLogger(RemotingServiceInvoker.class);

    private ArgumentAdjustor[] argumentAdjustors;

    private RemotingServiceValidator[] remotingServiceValidators;

    protected RemotingServiceLocator remotingServiceLocator;

    public abstract Object doInvoke(Object service, String methodName,
            Object[] args) throws Throwable;

    public RemotingServiceLocator getServiceLocator() {
        return remotingServiceLocator;
    }

    public Object invoke(final String serviceName, final String methodName,
            final Object[] args) throws Throwable {

        final Object service = remotingServiceLocator.getService(serviceName);
        if (!checkMethodArgumentsValidation(service, methodName, args)) {
            throw new InvaildServiceArgumentException(serviceName, methodName);
        }
        return doInvoke(service, methodName, args);
    }

    public void setArgumentAdjustors(final ArgumentAdjustor[] argumentAdjustors) {
        this.argumentAdjustors = argumentAdjustors;
    }

    public void setRemotingServiceValidators(
            RemotingServiceValidator[] remotingServiceValidators) {
        this.remotingServiceValidators = remotingServiceValidators;
    }

    public void setServiceLocator(final RemotingServiceLocator serviceLocator) {
        this.remotingServiceLocator = serviceLocator;
    }

    public boolean supports(final String serviceName, final String methodName,
            final Object[] args) {
        return canServiceCallValidation(serviceName, methodName, args)
                && remotingServiceLocator.isSupportService(serviceName);
    }

    private final Object adjustArgument(final Class clazz, final Object arg) {
        Object result = arg;
        for (int i = 0; i < argumentAdjustors.length; i++) {
            if (argumentAdjustors[i].isTarget(clazz, result)) {
                result = argumentAdjustors[i].adjust(clazz, result);
            }
        }
        return result;
    }

    private final void adjustArguments(final Class[] classes,
            final Object[] args) {
        if (argumentAdjustors.length > 0) {
            for (int i = 0; i < args.length; i++) {
                args[i] = adjustArgument(classes[i], args[i]);
            }
        }
    }

    private final boolean canServiceCallValidation(final String serviceName,
            final String methodName, final Object[] args) {
        boolean isValid = true;
        for (int i = 0; i < remotingServiceValidators.length; i++) {
            if (!remotingServiceValidators[i].isValidate(serviceName,
                    methodName, args)) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private final boolean checkMethodArgumentsValidation(final Object service,
            final String methodName, final Object[] args) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(service
                .getClass());
        final Method[] methods = beanDesc.getMethods(methodName);
        boolean isValid = false;

        for (int i = 0; i < methods.length; i++) {
            final Class[] classes = methods[i].getParameterTypes();
            if (classes.length == args.length) {
                adjustArguments(classes, args);
                isValid = true;
                break;
            }
        }

        return isValid;
    }

    protected final Object invokeServiceMethod(final Object service,
            final String methodName, final Object[] args) throws Throwable {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(service
                .getClass());
        try {
            return beanDesc.invoke(service, methodName, args);
        } catch (final Throwable throwable) {
            logger.debug("invoke service method exception", throwable);
            throw throwable;
        }
    }
}