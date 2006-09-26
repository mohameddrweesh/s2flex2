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
package org.seasar.flex2.core.format.amf.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf.io.reader.AmfDataReader;
import org.seasar.flex2.core.util.XmlUtil;
import org.w3c.dom.Document;

public class AmfXmlReaderImpl implements AmfDataReader {

    private static final String readXmlStringData(final DataInputStream inputStream) throws IOException {
        final int xmlStringLen = inputStream.readInt();
        final byte[] xmlStringBytes = new byte[xmlStringLen];
        inputStream.readFully(xmlStringBytes);
        return new String(xmlStringBytes);
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readXML(inputStream);
    }
    
    protected Document readXML(final DataInputStream inputStream)
            throws IOException {
        return XmlUtil.getXmlDocument( readXmlStringData(inputStream) );
    }
}