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
package org.seasar.flex2.util.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpSessionUtil {

    public static final String getSessionId(final HttpServletRequest request,
            final boolean isNewSessionCreate) {
        final String sessionId;

        if (request.isRequestedSessionIdValid()) {
            sessionId = request.getRequestedSessionId();
        } else {
            final HttpSession session = request.getSession(isNewSessionCreate);
            if (session != null) {
                sessionId = session.getId();
            } else {
                sessionId = null;
            }
        }

        return sessionId;
    }

    public static final String newSessionId(final HttpServletRequest request) {
        return request.getSession(true).getId();
    }
}