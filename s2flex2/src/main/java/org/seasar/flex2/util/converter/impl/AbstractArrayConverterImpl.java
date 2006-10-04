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
package org.seasar.flex2.util.converter.impl;

import java.lang.reflect.Array;
import java.util.Map;

public abstract class AbstractArrayConverterImpl extends
        AbstractMapConverterImpl {

    protected static final Object[] convertToBeanArray(Class clazz,
            Object[] targetArray) {
        final Object[] newArray = (Object[]) Array.newInstance(clazz,
                targetArray.length);
        final int arrayLength = targetArray.length;
        for (int i = 0; i < arrayLength; i++) {
            newArray[i] = newIncetance(clazz, (Map) targetArray[i]);
        }
        return newArray;
    }

    protected static final Object[] convertToTypedArray(Class clazz,
            Object[] targetArray) {
        final Object newArray = Array.newInstance(clazz, targetArray.length);
        if (targetArray.length > 0) {
            System.arraycopy(targetArray, 0, newArray, 0, targetArray.length);
        }
        return (Object[]) newArray;
    }

    protected static final Object convertToArray(final Object source,
            final Class distClass) {
        Object[] result = (Object[]) source;
        if (distClass != Object[].class) {
            if (result.length > 0
                    && result[0] instanceof Map
                    && !(Map.class.isAssignableFrom(distClass
                            .getComponentType()))) {
                result = convertToBeanArray(distClass.getComponentType(),
                        result);
            } else {
                result = convertToTypedArray(distClass.getComponentType(),
                        result);
            }
        }
        return result;
    }
}
