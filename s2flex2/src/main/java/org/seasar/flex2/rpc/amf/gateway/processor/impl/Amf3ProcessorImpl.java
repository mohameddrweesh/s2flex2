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
package org.seasar.flex2.rpc.amf.gateway.processor.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.seasar.flex2.rpc.amf.data.AmfMessage;
import org.seasar.flex2.rpc.amf.io.AmfReader;
import org.seasar.flex2.rpc.amf.io.AmfWriter;
import org.seasar.flex2.rpc.amf.io.impl.Amf3ReaderImpl;
import org.seasar.flex2.rpc.amf.io.impl.Amf3WriterImpl;
import org.seasar.flex2.rpc.gateway.processor.RequestProcessor;

public class Amf3ProcessorImpl extends AmfProcessorImpl implements RequestProcessor {

    protected AmfReader createReader(DataInputStream in) {
        return new Amf3ReaderImpl(in);
    }
    
    protected AmfWriter createWriter(AmfMessage responseMessage, DataOutputStream outputStream) {
        return new Amf3WriterImpl(outputStream, responseMessage);
    }
}