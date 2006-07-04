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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;

import org.seasar.flex2.core.format.amf3.type.ByteArray;
import org.seasar.flex2.core.format.amf3.type.factory.ByteArrayFactory;

public class Amf3ByteArrayReaderImpl extends AbstractAmf3ObjectReaderImpl {
    
    private static final int READ_BUFFER_SIZE = 1024 * 8;

    private ByteArrayFactory byteArrayFactory;
    
    public ByteArrayFactory getByteArrayFactory() {
        return byteArrayFactory;
    }

    public Object read(final DataInputStream inputStream) throws IOException {
        return readObject(inputStream);
    }

    public void setByteArrayFactory(ByteArrayFactory byteArrayFactory) {
        this.byteArrayFactory = byteArrayFactory;
    }

    protected final Object readInlinedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        ByteArray byteArray = readByteArrayData(reference, inputStream);
        addObjectReference(byteArray);
        return byteArray;
    }

    protected final Object readReferencedObject(final int reference,
            final DataInputStream inputStream) throws IOException {
        return getObjectAt(reference >>> 1);
    }

    private final ByteArray readByteArrayData(final int bytearrayDef,
            final DataInputStream inputStream) throws IOException {
        
        final int bytearrayLength = bytearrayDef >> 1;
        final byte[] bytearrayData = new byte[bytearrayLength];
        final byte[] readBuffer = new byte[READ_BUFFER_SIZE];
        
        int totalReadByteLength = 0;
        int readByteLength = 0;
        while( totalReadByteLength < bytearrayLength ){
            if( (bytearrayLength - totalReadByteLength) > READ_BUFFER_SIZE ){
                readByteLength = inputStream.read(readBuffer);
            } else {
                readByteLength = inputStream.read(readBuffer,0,(bytearrayLength - totalReadByteLength));
            }
            System.arraycopy(readBuffer, 0, bytearrayData, totalReadByteLength, readByteLength);
            totalReadByteLength += readByteLength;
        }
        
        return byteArrayFactory.createByteArray(bytearrayData);
    }
}