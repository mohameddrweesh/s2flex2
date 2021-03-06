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
package org.seasar.flex2.core.format.amf3.type;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public interface ByteArray extends ObjectInput, ObjectOutput {
    void compress();

    void flush();

    byte[] getBufferBytes();

    void initBuffer(byte[] bytes);

    void readBytes(byte[] bytes, int offset, int length) throws IOException;

    String readMultiByte(int length, String charSet) throws IOException;

    String readUTFBytes(int length) throws IOException;

    void reset();

    void uncompress();

    void writeBytes(byte[] bytes, int offset, int length) throws IOException;

    void writeMultiByte(String value, String charSet) throws IOException;

    void writeUnsignedInt(int value) throws IOException;

    void writeUTFBytes(String value) throws IOException;
}
