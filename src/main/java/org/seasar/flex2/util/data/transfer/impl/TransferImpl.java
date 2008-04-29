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
package org.seasar.flex2.util.data.transfer.impl;

import org.seasar.flex2.util.data.storage.Storage;
import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.flex2.util.data.transfer.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.util.data.transfer.annotation.handler.AnnotationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * TrasferInterfaceの実装クラス
 * @author e1.arkw
 *
 */
public class TransferImpl implements Transfer {
    /**
     * アノテーションハンドラ
     */
    private static final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();
    /**
     * 指定されたstorageの名称とtypeが一致するかどうかチェックします
     * @param storage
     * @param type
     * @return 転送対象のObjectであればtrue,それ以外はfalse
     */
    private static final boolean isTransferTarget(final Storage storage,
            final String type) {
        return ((type != null) && storage.getName().equalsIgnoreCase(type));
    }
    /**
     * 指定されたObjectを転送先のストレージにコピーします。
     * 
     * @param target 転送対象Ojbect
     * @param storage 転送先のストレージ
     */
    public void exportToStorage(final Object target, final Storage storage) {
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            // if (propertyDesc.isReadable()) {
            if (propertyDesc.hasReadMethod()) {
                final String type = annotationHandler.getExportStorageType(
                        beanDesc, propertyDesc);
                if (isTransferTarget(storage, type)) {
                    storage.setProperty(propertyDesc.getPropertyName(),
                            propertyDesc.getValue(target));
                }
            }
        }
    }
    /**
     * 指定のstorageからtargetとなるComponentを取り出します。
     * @param storage 転送元ストレージ
     * @param target 取り出す対象のObject
     */
    public void importToComponent(final Storage storage, final Object target) {
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
//            if (propertyDesc.isWritable()) {
              if (propertyDesc.hasWriteMethod()) {
                final String type = annotationHandler.getImportStorageType(
                        beanDesc, propertyDesc);
                if (isTransferTarget(storage, type)) {
                    propertyDesc.setValue(target, storage
                            .getProperty(propertyDesc.getPropertyName()));
                }
            }
        }
    }
}