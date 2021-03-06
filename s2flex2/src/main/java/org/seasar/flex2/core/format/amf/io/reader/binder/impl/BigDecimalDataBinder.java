/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf.io.reader.binder.impl;

import java.math.BigDecimal;

import org.seasar.flex2.core.format.amf.io.reader.binder.DataBinder;

public class BigDecimalDataBinder implements DataBinder {

    public Object bind(final Object source, final Class bindClass) {
        return new BigDecimal((String) source);
    }

    public boolean isTarget(final Object targetValue, final Class bindClass) {
        return (bindClass == BigDecimal.class)
                && (targetValue.getClass() == String.class);
    }

}
