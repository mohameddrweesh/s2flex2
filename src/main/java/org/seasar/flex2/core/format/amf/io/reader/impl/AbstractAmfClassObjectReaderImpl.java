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

    private static final Object createBeanObject(final AmfObject amfObject) {
        final Class clazz = ClassUtil.forName((String) amfObject
                .get(AmfConstants.REMOTE_CLASS));
        return ClassUtil.newInstance(clazz);
    }

    private void translateBeanProperty(final BeanDesc beanDesc,
            final Object bean, final String key, final AmfObject amfObject) {
        if (beanDesc.hasPropertyDesc(key)) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(key);
            if (pd.hasWriteMethod()) {
                pd.setValue(bean, amfObject.get(key));
            }
        }
    }

    protected final Object translateBean(final AmfObject amfObject) {
        final Object bean = createBeanObject(amfObject);
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        for (final Iterator i = amfObject.keySet().iterator(); i.hasNext();) {
            translateBeanProperty(beanDesc, bean, (String) i.next(), amfObject);
        }
        return bean;
    }

}