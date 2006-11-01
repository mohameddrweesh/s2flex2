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
package org.seasar.flex2.util.converter.impl;

import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.util.converter.Converter;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;

public abstract class AbstractBeanConverterImpl implements Converter {

    private final static void copyProperties( final Map src, final Object dest) {
        if (src == null || dest == null) {
            return;
        }
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
        for (Iterator i = src.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (beanDesc.hasPropertyDesc(key)) {
                PropertyDesc pd = beanDesc.getPropertyDesc(key);
                if (pd.hasWriteMethod()) {
                    pd.setValue(dest, src.get(key));
                }
            }
        }
    }

    protected static final Object newIncetance(final Class clazz,
            final Map initProperties) {
        final Object bean = ClassUtil.newInstance(clazz);
        copyProperties(initProperties, bean);
        return bean;
    }

    public abstract Object convert(Object source, Class distClass);

}
