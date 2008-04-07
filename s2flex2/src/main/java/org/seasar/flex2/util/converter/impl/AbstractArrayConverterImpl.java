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

import java.lang.reflect.Array;
import java.util.Map;

public abstract class AbstractArrayConverterImpl extends
        AbstractBeanConverterImpl {

    protected static final Object convertToArray(final Object source,
            final Class distClass) {
        Object result = source;
        if (source != null && distClass != Object[].class
                && source.getClass().isArray()) {
            result = doConvertToArray((Object[]) source, distClass);
        }
        return result;
    }

    private static final Object[] doConvertToArray(final Object[] source,
            final Class distClass) {
        Object[] result = source;
        if (source.length > 0) {
            if ((result[0] instanceof Map)
                    && !(Map.class.isAssignableFrom(distClass
                            .getComponentType()))) {
                result = convertToBeanArray(distClass.getComponentType(),
                        result);
            } else {
                result = convertToTypedArray(distClass.getComponentType(),
                        result);
            }
        } else {
            result = (Object[]) Array.newInstance(distClass.getComponentType(), 0);
        }
        return result;
    }

    protected static final Object[] convertToBeanArray(final Class clazz,
            final Object[] targetArray) {
        final int length = targetArray.length;
        final Object[] newArray = (Object[]) Array.newInstance(clazz, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = newIncetance(clazz, (Map) targetArray[i]);
        }
        return newArray;
    }

    protected static final Object[] convertToTypedArray(final Class clazz,
            final Object[] targetArray) {
        final int length = targetArray.length;
        final Object[] newArray = (Object[])Array.newInstance(clazz, length);
        System.arraycopy(targetArray, 0, newArray, 0, length);
        return newArray;
    }
}
