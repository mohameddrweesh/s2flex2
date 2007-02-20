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
package org.seasar.flex2.core.format.amf.binder.impl;

import org.seasar.flex2.core.format.amf.binder.DataBinder;
import org.seasar.flex2.core.format.amf3.type.ByteArray;
import org.seasar.flex2.core.format.amf3.type.factory.ByteArrayFactory;

public class ByteArrayDataBinder implements DataBinder {

    private ByteArrayFactory byteArrayFactory;

    public Object bind(final Object source) {
        return byteArrayFactory.createByteArray((byte[]) source);
    }

    public boolean isTarget(final Class sourceClass, final Class bindClass) {
        return (bindClass == ByteArray.class) && (sourceClass == byte[].class);
    }

    public void setByteArrayFactory(ByteArrayFactory byteArrayFactory) {
        this.byteArrayFactory = byteArrayFactory;
    }

}
