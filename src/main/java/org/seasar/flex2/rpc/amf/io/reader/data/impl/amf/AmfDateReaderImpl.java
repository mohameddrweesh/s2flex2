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
package org.seasar.flex2.rpc.amf.io.reader.data.impl.amf;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import org.seasar.flex2.rpc.amf.data.AmfConstants;
import org.seasar.flex2.rpc.amf.io.reader.data.AmfDataReader;
import org.seasar.framework.log.Logger;

public class AmfDateReaderImpl implements AmfDataReader {

    private static final Logger logger = Logger
            .getLogger(AmfDateReaderImpl.class);

    public Object read( final DataInputStream inputStream) throws IOException {
        return readDate( inputStream );
    }

    private final Date readDate( final DataInputStream inputStream) throws IOException {
        double ms = inputStream.readDouble();
        int offset = inputStream.readUnsignedShort()
                * AmfConstants.MILLS_PER_HOUR;
        int defaultOffset = TimeZone.getDefault().getRawOffset();
        ms += (double) defaultOffset - offset;
        Date date = new Date((long) ms);
        
        logger.debug("readDate:" + date);
        return date;
    }
}