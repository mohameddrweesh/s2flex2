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
package org.seasar.flex2.core.format.amf.io.reader.impl;

import java.util.Iterator;

import org.seasar.flex2.core.format.amf.AmfConstants;
import org.seasar.flex2.core.format.amf.type.AmfObject;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public abstract class AbstractAmfClassObjectReaderImpl extends
        AbstractAmfObjectReaderImpl {

    protected final Object translateBean(AmfObject amfObject) {
        String type = (String) amfObject.get(AmfConstants.REMOTE_CLASS);
        Class clazz = ClassUtil.forName(type);
        Object bean = ClassUtil.newInstance(clazz);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        for (Iterator i = amfObject.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (beanDesc.hasPropertyDesc(key)) {
                Object value = amfObject.get(key);
                PropertyDesc pd = beanDesc.getPropertyDesc(key);
                if (pd.hasWriteMethod()) {
                    pd.setValue(bean, value);
                }
            }
        }
        return bean;
    }

}