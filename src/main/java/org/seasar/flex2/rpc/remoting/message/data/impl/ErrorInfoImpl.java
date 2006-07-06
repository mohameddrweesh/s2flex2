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
package org.seasar.flex2.rpc.remoting.message.data.impl;

import org.seasar.flex2.rpc.remoting.message.data.ErrorInfo;

public class ErrorInfoImpl implements ErrorInfo {

    private static String getStackTraceString(Throwable t) {
        StackTraceElement[] elements = t.getStackTrace();
        StringBuffer buf = new StringBuffer(t.toString());
        buf.append('\n');
        for (int i = 0; i < elements.length; ++i) {
            buf.append(elements[i].toString());
            buf.append('\n');
        }
        return buf.toString();
    }

    private final String code = "SERVER.PROCESSING";

    private final String description;

    private final String details;

    private final String level = "error";

    private final String type;

    public ErrorInfoImpl(Throwable t) {
        type = t.getClass().getName();
        details = getStackTraceString(t);
        description = t.getMessage();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getDetails() {
        return details;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }
}