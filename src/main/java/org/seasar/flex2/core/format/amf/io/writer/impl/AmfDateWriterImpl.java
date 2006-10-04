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
import java.util.Date;
import java.util.TimeZone;

import org.seasar.flex2.core.format.amf.AmfConstants;
import org.seasar.flex2.core.format.amf.io.writer.AmfDataWriter;
import org.seasar.flex2.core.format.amf.type.AmfTypeDef;

public class AmfDateWriterImpl implements AmfDataWriter {

    private static final void write(final Date value,
            final DataOutputStream outputStream) throws IOException {
        outputStream.writeByte(AmfTypeDef.DATE);
        outputStream.writeDouble((value).getTime());
        final int offset = TimeZone.getDefault().getRawOffset();
        outputStream.writeShort(offset / AmfConstants.MILLS_PER_HOUR);
    }

    public void write(final Object value, final DataOutputStream outputStream)
            throws IOException {
        write((Date) value, outputStream);
    }
}