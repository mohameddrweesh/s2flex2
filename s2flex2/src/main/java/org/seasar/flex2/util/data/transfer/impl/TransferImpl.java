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
 *  {@link Tranfer}Tranferの実装クラス
 * @author e1.arkw
 * @author nod
 *
 */
public class TransferImpl implements Transfer {
    /** アノテーションハンドラ. 実行環境によって定数アノテーションかjava5.0のアノテーションのいづれかのハンドラがセットされます。*/
    private static final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();
    /**
     * 指定されたstorageの名称とtypeが一致するかどうかチェックします
     * @param storage 保存対象のStorage
     * @param type ストレージのタイプ.{@link Storage}によって定義されている値
     * @return 転送対象のときはtrue,それ以外はfalse.
     */
    private static final boolean isTransferTarget(final Storage storage,
            final String type) {
        return ((type != null) && storage.getName().equalsIgnoreCase(type));
    }

    /**
     * targetになるObjectのreadMethodにExportアノテーションが存在するとき
     * ComponentsをStorageに保存します。
     *  
     *  @param target Storageに保存するオブジェクト
     *  @param storage 保存先のストレージ
     */
    public void exportToStorage(final Object target, final Storage storage) {
        //ExportアノテーションがgetMethodにあるときにはStorageにComponentを保存する。
        /*
         * @Export(storage=StorageType.SESSION)
         * public AddDto getAddDto() {
         *       return addDto;
         * }
         * 上記のように書かれてときには、Sessionに対してaddDtoが保存される。
         */
        
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());

        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.isReadable()) {
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
     * 指定されたObjectのwriteMethodにImportアノテーションが存在しているとき
     * StorageからComponentにセットします。
     * 
     * @param storage 保存されているストレージ
     * @param target セットする対象のオブジェクト
     */
    public void importToComponent(final Storage storage, final Object target) {
        /*
         * @Import(storage=StorageType.SESSION)
         * public AddDto setAddDto(AddDto addDto) {
         *       this.addDto = addDto;
         * }
         * 上記のように書かれてときには、Sessionデータから取得したObjectがセットされる。
         */
        
        //ImportアノテーションがコンポーネントのsetMethodについているときに、
        //Storageから取得したComponentをセットする。
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.isWritable()) {
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