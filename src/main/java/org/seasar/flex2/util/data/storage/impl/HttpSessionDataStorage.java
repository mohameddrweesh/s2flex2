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

import javax.servlet.http.HttpSession;

import org.seasar.flex2.util.data.storage.Storage;

public class HttpSessionDataStorage implements Storage {

    private final static String SESSION = "session";

    private final HttpSession session;

    public HttpSessionDataStorage(HttpSession session) {
        this.session = session;
    }

    public String getName() {
        return SESSION;
    }

    public Object getProperty( final String name) {
        return session.getAttribute(name);
    }

    public Enumeration getPropertyNames() {

        return session.getAttributeNames();
    }

    public void setProperty( final String name, final Object value) {
        session.setAttribute(name, value);
    }
}
