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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AmfCollectionWriterImpl extends AmfArrayWriterImpl {

    public void write(Object value, DataOutputStream outputStream)
            throws IOException {
        write((Collection) value, outputStream);
    }

    protected void write(Collection value, DataOutputStream outputStream)
            throws IOException {
        ArrayList list = new ArrayList();
        list.addAll(value);
        write(list.toArray(new Object[list.size()]), outputStream);
    }
}