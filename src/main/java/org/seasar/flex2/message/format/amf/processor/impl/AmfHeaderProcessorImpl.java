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
package org.seasar.flex2.message.format.amf.processor.impl;

import java.util.Iterator;
import java.util.Map;

import org.seasar.flex2.message.format.amf.data.AmfHeader;
import org.seasar.flex2.message.format.amf.data.AmfMessage;
import org.seasar.flex2.message.format.amf.data.factory.AmfHeaderFactory;
import org.seasar.flex2.message.format.amf.processor.AmfHeaderProcessor;

public class AmfHeaderProcessorImpl implements AmfHeaderProcessor {

    private AmfHeaderFactory headerFactory;

    public AmfHeaderFactory getHeaderFactory() {
        return headerFactory;
    }

    public void processRequest(AmfMessage requestMessage, Map addHeaders) {

    }

    public void processResponse(AmfMessage responseMessage, Map addHeaders) {
        Iterator headerIt = addHeaders.entrySet().iterator();
        Map.Entry header;
        while (headerIt.hasNext()) {
            header = (Map.Entry) headerIt.next();
            addHeader(responseMessage, (String) header.getKey(),
                    (String) header.getValue());
        }
    }

    public void setHeaderFactory(AmfHeaderFactory headerFactory) {
        this.headerFactory = headerFactory;
    }

    private final void addHeader(AmfMessage responseMessage, String headerName,
            String data) {
        AmfHeader header = headerFactory.createHeader(headerName, data);
        responseMessage.addHeader(header);
    }

}