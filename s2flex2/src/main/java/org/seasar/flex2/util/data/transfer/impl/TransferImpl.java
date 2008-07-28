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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.util.data.storage.Storage;
import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.flex2.util.data.transfer.annotation.factory.AnnotationHandlerFactory;
import org.seasar.flex2.util.data.transfer.annotation.handler.AnnotationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * {@link Tranfer}Tranferの実装クラス
 * 
 * @author e1.arkw
 * @author nod
 * 
 */
public class TransferImpl implements Transfer {

    /**
     * アノテーションハンドラ. 実行環境によって定数アノテーションかjava5.0のアノテーションのいづれかのハンドラがセットされます.
     */
    private static final AnnotationHandler annotationHandler = AnnotationHandlerFactory
            .getAnnotationHandler();

    /**
     * 指定されたstorageの名称とtypeが一致するかどうかチェックします
     * @param storage
     *            保存対象のStorage
     * @param type
     *            ストレージのタイプ.{@link Storage}によって定義されている値
     * @return 転送対象のときはtrue,それ以外はfalse.
     */
    private static final boolean isTransferTarget(final Storage storage,
            final String type) {
        return ((type != null) && storage.getName().equalsIgnoreCase(type));
    }

    /**
     * targetのExportアノテーションが指定されているプロパティをStorageにエクスポートします.
     * 
     * @Export(storage=StorageType.SESSION) public AddDto addDto;
     * 
     * 上記のように書かれてときには、Sessionに対してプロパティ名"addDto"でaddDtoがエクスポートされる。
     * 
     * @param target
     *            エクスポート対象オブジェクト
     * 
     * @param storage
     *            保存先のストレージ
     */
    public void exportToStorage(final Object target, final Storage storage) {
        final Class resultClass = target.getClass();
        do{
            if( resultClass.isArray() ){
                Object[] resultArray = (Object[])target;
                for (int index = 0; index < resultArray.length; index++) {
                    processExportTo(resultArray[index], storage);
                }
                break;
            }
            if( resultClass.isAssignableFrom(Map.class) ){
                Collection values = ((Map)target).values();
                Iterator ite = values.iterator();
                while (ite.hasNext()) {
                    processExportTo(ite.next(), storage);
                }
                
                break;
            }
            processExportTo(target, storage);
        } while( false );
    }

    /**
     * @param target
     * @param storage
     */
    private void processExportTo(final Object target, final Storage storage) {
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());
        
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.isReadable() || propertyDesc.hasReadMethod()) {
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
     * StorageからtargetにImportアノテーションが指定されているプロパティに値をインポートします.
     * 
     * @Import(storage=StorageType.SESSION) public AddDto addDto;
     * 
     * 上記のように書かれてときには、Sessionデータからプロパティ名"addDto"で取得したObjectがインポートされる。
     * 
     * @param storage
     *            保存されているストレージ
     * 
     * @param target
     *            インポート対象オブジェクト
     */
    public void importToComponent(final Storage storage, final Object target) {
        final Class resultClass = target.getClass();
        do{
            if( resultClass.isArray() ){
                Object[] resultArray = (Object[])target;
                for (int index = 0; index < resultArray.length; index++) {
                    processImport(storage, resultArray[index]);
                }
                break;
            }
            if( resultClass.isAssignableFrom(Map.class) ){
                Collection values = ((Map)target).values();
                Iterator ite = values.iterator();
                while (ite.hasNext()) {
                    processImport(storage, ite.next());

                }
                
                break;
            }
            processImport(storage, target);
        } while( false );
        
    }

    /**
     * @param storage
     * @param target
     */
    private void processImport(final Storage storage, final Object target) {
        final BeanDesc beanDesc = BeanDescFactory
                .getBeanDesc(target.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
            if (propertyDesc.isWritable() || propertyDesc.hasWriteMethod()) {
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