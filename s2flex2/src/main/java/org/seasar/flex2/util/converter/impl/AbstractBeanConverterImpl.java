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
package org.seasar.flex2.util.converter.impl;

import java.util.Map;

import org.seasar.flex2.util.converter.Converter;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.util.ClassUtil;

/**
 * Bean変換を行う抽象クラスです。
 * @author e1.arkw
 *
 */
public abstract class AbstractBeanConverterImpl implements Converter {
    /**
     * マップデータから指定されたクラス　のインスタンスを生成します
     * @param clazz インスタンス化するクラス
     * @param initProperties 初期データ
     * @return 初期データからインスタンス化されたオブジェクト
     */
    protected static final Object newIncetance(final Class clazz,
            final Map initProperties) {
        final Object bean = ClassUtil.newInstance(clazz);
        BeanUtil.copyProperties(initProperties, bean);
        return bean;
    }
    

    public abstract Object convert(Object source, Class distClass);

}
