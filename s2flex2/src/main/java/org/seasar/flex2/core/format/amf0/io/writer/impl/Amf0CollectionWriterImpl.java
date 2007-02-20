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
package org.seasar.flex2.core.format.amf0.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Amf0CollectionWriterImpl extends Amf0ArrayWriterImpl {

    public boolean isWritableValue(final Object value) {
        return Collection.class.isAssignableFrom(value.getClass());
    }

    protected void writeObjectData(final Object value,
            final DataOutputStream outputStream) throws IOException {
        final ArrayList list = new ArrayList();
        list.addAll((Collection) value);
        super.writeObjectData(list.toArray(new Object[list.size()]),
                outputStream);
    }
}