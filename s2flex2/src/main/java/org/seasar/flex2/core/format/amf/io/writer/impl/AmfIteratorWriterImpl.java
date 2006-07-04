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
package org.seasar.flex2.core.format.amf.io.writer.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;




public class AmfIteratorWriterImpl extends AmfArrayWriterImpl {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        write((Iterator) value, outputStream);
    }

    protected void write(Iterator value, DataOutputStream outputStream)
            throws IOException {
        ArrayList list = new ArrayList();
        while (value.hasNext()) {
            list.add(value.next());
        }
        write(list.toArray(new Object[list.size()]), outputStream);
    }
}