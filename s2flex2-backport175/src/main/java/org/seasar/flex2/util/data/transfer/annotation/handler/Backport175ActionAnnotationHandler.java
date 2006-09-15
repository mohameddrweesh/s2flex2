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
package org.seasar.flex2.util.data.transfer.annotation.handler;

import java.lang.reflect.Method;

import org.codehaus.backport175.reader.Annotation;
import org.codehaus.backport175.reader.Annotations;
import org.seasar.flex2.util.data.transfer.annotation.Export;
import org.seasar.flex2.util.data.transfer.annotation.Import;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;

public class Backport175ActionAnnotationHandler implements AnnotationHandler {

    public String getExportStorageType(final BeanDesc beanDesc,
            final PropertyDesc propertyDesc) {
        String storage = null;
        final Method method = propertyDesc.getReadMethod();
        if (method != null) {
            final Annotation annotation = Annotations.getAnnotation(
                    Export.class, method);
            if (annotation != null) {
                storage = ((Export) annotation).storage();
            }
        }
        return storage;
    }

    public String getImportStorageType(final BeanDesc beanDesc,
            final PropertyDesc propertyDesc) {
        String storage = null;
        final Method method = propertyDesc.getWriteMethod();
        if (method != null) {
            final Annotation annotation = Annotations.getAnnotation(
                    Import.class, method);
            if (annotation != null) {
                storage = ((Import) annotation).storage();
            }
        }
        return storage;
    }
}