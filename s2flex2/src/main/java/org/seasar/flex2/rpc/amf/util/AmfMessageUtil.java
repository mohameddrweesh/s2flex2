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
package org.seasar.flex2.rpc.amf.util;

import org.seasar.flex2.rpc.amf.data.AmfBody;
import org.seasar.flex2.rpc.amf.data.AmfConstants;
import org.seasar.flex2.rpc.amf.data.impl.AmfBodyImpl;
import org.seasar.flex2.rpc.amf.data.impl.AmfErrorImpl;

public class AmfMessageUtil {

    public static AmfBody createResultResponseBody(String target, Object result) {
        return new AmfBodyImpl(target + AmfConstants.RESPONSE_RESULT,
                AmfConstants.RESPONSE_NULL, result);
    }
    
    public static AmfBody createStatusResponseBody(String target, Throwable t) {
        return new AmfBodyImpl(target + AmfConstants.RESPONSE_STATUS,
                AmfConstants.RESPONSE_NULL, new AmfErrorImpl(t));
    }
}
