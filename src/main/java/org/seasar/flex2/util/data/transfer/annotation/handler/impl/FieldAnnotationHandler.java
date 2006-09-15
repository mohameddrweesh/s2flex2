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
package org.seasar.flex2.util.data.transfer.annotation.handler.impl;

import java.lang.reflect.Field;

import org.seasar.flex2.util.data.transfer.annotation.handler.AnnotationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.util.FieldUtil;

public class FieldAnnotationHandler implements AnnotationHandler {

    public static final String EXPORT = "_EXPORT";

    public static final String IMPORT = "_IMPORT";

    public String getExportStorageType(final BeanDesc beanDesc,
            final PropertyDesc propertyDesc) {
        return getStorageType(beanDesc, propertyDesc, EXPORT);
    }

    public String getImportStorageType(final BeanDesc beanDesc,
            final PropertyDesc propertyDesc) {
        return getStorageType(beanDesc, propertyDesc, IMPORT);
    }

    private final String getStorageType(final BeanDesc beanDesc,
            final PropertyDesc propertyDesc, final String type) {
        final String annotation = propertyDesc.getPropertyName() + type;
        String exportStorageType = null;
        if (beanDesc.hasField(annotation)) {
            final Field field = beanDesc.getField(annotation);
            exportStorageType = (String) FieldUtil.get(field, null);
        }
        return exportStorageType;
    }
}
