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
package org.seasar.flex2.util.data.transfer.impl;

import java.util.Enumeration;

import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.flex2.util.data.transfer.annotation.handler.AnnotationHandler;
import org.seasar.flex2.util.data.transfer.annotation.handler.AnnotationHandlerFactory;
import org.seasar.flex2.util.data.transfer.storage.Storage;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class TransferImpl implements Transfer {

    private final AnnotationHandler handler = AnnotationHandlerFactory
            .getAnnotationHandler();

    public void importTo(Storage storage, Object target) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(target.getClass());

        Enumeration prop_names = storage.getPropertyNames();
        while (prop_names.hasMoreElements()) {
            String prop_name = (String) prop_names.nextElement();
            if (beanDesc.hasPropertyDesc(prop_name)) {
                PropertyDesc propertyDesc = beanDesc.getPropertyDesc(prop_name);
                String type = handler.getImportStorageType(beanDesc,
                        propertyDesc);
                if (isTransfer(type, storage)) {
                    if (propertyDesc.hasWriteMethod()) {
                        propertyDesc.setValue(target, storage
                                .getProperty(prop_name));
                    }
                }
            }
        }
    }

    public void exportTo(Storage storage, Object target) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(target.getClass());

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (!propertyDesc.hasReadMethod()) {
                break;
            }
            String type = handler.getExportStorageType(beanDesc, propertyDesc);
            if (isTransfer(type, storage)) {
                String propertyName = propertyDesc.getPropertyName();
                Object propertyValue = propertyDesc.getValue(target);
                storage.setProperty(propertyName, propertyValue);
            }
        }
    }

    private final boolean isTransfer(String type, Storage storage) {
        return (type != null && storage.getName().equalsIgnoreCase(type));
    }
}