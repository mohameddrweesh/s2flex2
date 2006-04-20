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
package org.seasar.flex2.util.transfer.annotation.handler;

import java.lang.reflect.Method;

import org.seasar.flex2.util.transfer.annotation.Export;
import org.seasar.flex2.util.transfer.annotation.Import;
import org.seasar.flex2.util.transfer.annotation.handler.impl.BasicAnnotationHandlerImpl;
import org.seasar.framework.beans.PropertyDesc;

public class TigerAnnotationHandler extends BasicAnnotationHandlerImpl {
    public String getExportStorageType(final PropertyDesc propertyDesc) {
        Method method = propertyDesc.getReadMethod();
        Export storageType = method.getAnnotation(Export.class);
        if (storageType != null) {
            return storageType.storage();
        }

        return null;
    }
    
    public String getImportStorageType(final PropertyDesc propertyDesc) {
        Method method = propertyDesc.getWriteMethod();
        Import storageType = method.getAnnotation(Import.class);
        if (storageType != null) {
            return storageType.storage();
        }

        return null;
    }
}
