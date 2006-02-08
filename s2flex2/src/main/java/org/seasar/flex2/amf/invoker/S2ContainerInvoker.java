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
package org.seasar.flex2.amf.invoker;

import org.seasar.flex2.amf.ServiceInvoker;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.ClassUtil;

/**
 * @author higa
 *  
 */
public class S2ContainerInvoker implements ServiceInvoker {

	public S2ContainerInvoker() {
	}

	/**
	 * @see org.seasar.amf.ServiceInvoker#supports(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public boolean supports(String serviceName, String methodName, Object[] args) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		if (container.hasComponentDef(serviceName)) {
			return true;
		}
		try {
			Class clazz = ClassUtil.forName(serviceName);
			if (container.hasComponentDef(clazz)) {
				return true;
			}
		} catch (Throwable ignore) {
		}
		return false;
	}

	/**
	 * @see org.seasar.amf.ServiceInvoker#invoke(java.lang.String,
	 *      java.lang.String, java.lang.Object)
	 */
	public Object invoke(String serviceName, String methodName, Object[] args)
			throws Throwable {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		Object component = null;
		Class clazz = null;
		if (container.hasComponentDef(serviceName)) {
			component = container.getComponent(serviceName);
		} else {
			clazz = ClassUtil.forName(serviceName);
			component = container.getComponent(clazz);
		}
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
		return beanDesc.invoke(component, methodName, args);
	}

}