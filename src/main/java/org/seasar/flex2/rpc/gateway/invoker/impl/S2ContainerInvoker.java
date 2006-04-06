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
package org.seasar.flex2.rpc.gateway.invoker.impl;

import org.seasar.flex2.rpc.gateway.invoker.ServiceInvoker;
import org.seasar.flex2.rpc.gateway.invoker.exception.ServiceNotFoundRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.InvocationTargetRuntimeException;
import org.seasar.framework.util.ClassUtil;

public class S2ContainerInvoker implements ServiceInvoker {
    
    private S2Container container;
    
	public S2ContainerInvoker() {
	}
    
    public void setContainer(S2Container container) {
        this.container = container;
    }
    
    public S2Container getContainer(){
        return this.container;
    }

	public boolean supports(String serviceName, String methodName, Object[] args) {
		S2Container root = container.getRoot();
		if (root.hasComponentDef(serviceName)) {
			return true;
		}
		try {
			Class clazz = ClassUtil.forName(serviceName);
			if (root.hasComponentDef(clazz)) {
				return true;
			}
		} catch (Throwable ignore) {
		}
		return false;
	}

	public Object invoke(String serviceName, String methodName, Object[] args)
			throws Throwable {

		Object component = findComponent(serviceName);
		return invokeServiceMethod(methodName, args, component);
	}

    protected Object invokeServiceMethod(String methodName, Object[] args, Object component) throws Throwable {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
        try {
            return beanDesc.invoke(component, methodName, args);
        } catch (InvocationTargetRuntimeException e) {
            throw e.getCause();
        }
    }

    protected Object findComponent(String serviceName) {
        S2Container root = container.getRoot();
		Object component = null;
		Class clazz = null;
		if (root.hasComponentDef(serviceName)) {
			component = root.getComponent(serviceName);
		} else {
			clazz = ClassUtil.forName(serviceName);
			component = root.getComponent(clazz);
		}
        
        if (component == null) {
            throw new ServiceNotFoundRuntimeException(serviceName);
        }
        
        return component;
    }
}