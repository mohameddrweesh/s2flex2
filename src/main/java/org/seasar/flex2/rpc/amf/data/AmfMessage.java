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
package org.seasar.flex2.rpc.amf.data;

public interface AmfMessage {

    int getBodySize();

    AmfBody getBody(int index);

    void addBody(AmfBody body);

    int getVersion();

    void setVersion(int amf_version);

    int getHeaderSize();

    AmfHeader getHeader(int index);

    void addHeader(AmfHeader body);
}
