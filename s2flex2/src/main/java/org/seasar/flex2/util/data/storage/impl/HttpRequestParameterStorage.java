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
package org.seasar.flex2.util.data.storage.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.seasar.flex2.util.data.storage.Storage;

public class HttpRequestParameterStorage implements Storage {

    private final static String REQUEST = "request";

    private final HttpServletRequest request;

    public HttpRequestParameterStorage(final HttpServletRequest request) {
        this.request = request;
    }

    public String getName() {
        return REQUEST;
    }

    public Object getProperty(final String name) {
        return request.getParameter(name);
    }

    public Enumeration getPropertyNames() {

        return request.getParameterNames();
    }

    public void setProperty(final String name, final Object value) {
    }
}
