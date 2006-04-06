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
package org.seasar.flex2.util.data.transfer;

import java.util.Enumeration;

import org.seasar.flex2.util.data.transfer.annotation.AnnotationHandler;
import org.seasar.flex2.util.data.transfer.annotation.AnnotationHandlerFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.impl.BeanDescImpl;

public class Transfer {

    public static void importTo(Storage storage, Object target) {
        BeanDesc beanDesc = new BeanDescImpl(target.getClass());
        AnnotationHandler handler = AnnotationHandlerFactory
                .getAnnotationHandler();

        Enumeration names = storage.getPropertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (beanDesc.hasPropertyDesc(name)) {
                PropertyDesc propertyDesc = beanDesc.getPropertyDesc(name);
                String type = handler.getImportStorageType(beanDesc,
                        propertyDesc);
                if (type != null && storage.getName().equals(type)) {
                    if (propertyDesc.hasWriteMethod()) {
                        propertyDesc
                                .setValue(target, storage.getProperty(name));
                    }
                }
            }
        }
    }

    public static void exportTo(Storage storage, Object target) {
        BeanDesc beanDesc = new BeanDescImpl(target.getClass());
        AnnotationHandler handler = AnnotationHandlerFactory
                .getAnnotationHandler();

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (!propertyDesc.hasReadMethod()) {
                break;
            }
            String type = handler.getExportStorageType(beanDesc, propertyDesc);
            if (type != null && storage.getName().equals(type)) {
                String propertyName = propertyDesc.getPropertyName();
                Object propertyValue = propertyDesc.getValue(target);
                storage.setProperty(propertyName, propertyValue);
            }
        }
    }

}