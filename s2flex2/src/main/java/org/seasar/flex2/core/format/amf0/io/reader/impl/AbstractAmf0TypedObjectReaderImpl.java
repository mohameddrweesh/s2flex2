/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf0.io.reader.impl;

import java.util.Iterator;

import org.seasar.flex2.core.format.amf.io.reader.binder.DataBinder;
import org.seasar.flex2.core.format.amf0.Amf0Constants;
import org.seasar.flex2.core.format.amf0.type.Amf0Object;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public abstract class AbstractAmf0TypedObjectReaderImpl extends
        AbstractAmf0ObjectReaderImpl {

    private static final Object createBeanObject(final Amf0Object amfObject) {
        final Class clazz = ClassUtil.forName((String) amfObject
                .get(Amf0Constants.REMOTE_CLASS));
        return ClassUtil.newInstance(clazz);
    }

    protected DataBinder[] dataBinders;

    public void setDataBinders(final DataBinder[] dataBinders) {
        this.dataBinders = dataBinders;
    }

    protected final void setupProperty(final Object object,
            final PropertyDesc propertyDef, final Object propertyValue) {
        if (propertyValue != null) {
            Object setValue = propertyValue;
            for (int i = 0; i < dataBinders.length; i++) {
                DataBinder binder = dataBinders[i];
                if (binder.isTarget(propertyValue, propertyDef
                        .getPropertyType())) {
                    setValue = binder.bind(propertyValue, propertyDef
                            .getPropertyType());
                    break;
                }
            }
            propertyDef.setValue(object, setValue);
        }
    }

    protected final Object translateBean(final Amf0Object amfObject) {
        final Object bean = createBeanObject(amfObject);
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        for (final Iterator i = amfObject.keySet().iterator(); i.hasNext();) {
            translateBeanProperty(beanDesc, bean, (String) i.next(), amfObject);
        }
        return bean;
    }

    private final void translateBeanProperty(final BeanDesc beanDesc,
            final Object bean, final String key, final Amf0Object amfObject) {
        if (beanDesc.hasPropertyDesc(key)) {
            final PropertyDesc pd = beanDesc.getPropertyDesc(key);
            if (pd.isWritable()) {
                setupProperty(bean, pd, amfObject.get(key));
            }
        }
    }
}