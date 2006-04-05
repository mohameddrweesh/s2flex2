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
package org.seasar.flex2.rpc.amf.gateway;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Amf3Gateway extends AmfGateway {

    public Amf3Gateway() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // filterHeader( request );
        checkSession(request);

        super.doPost(request, response);
    }

    private void filterHeader(HttpServletRequest request) {
        Enumeration e = request.getHeaderNames();
        while (e.hasMoreElements()) {
            String elementName = (String) e.nextElement();
            System.out.println(elementName + ":"
                    + request.getHeader(elementName));
        }
        String flash_version = (String) request.getHeader("x-flash-version");
        if (flash_version != null) {
            StringTokenizer st = new StringTokenizer(flash_version, ",");
            int st_count = st.countTokens();
            int[] versions = new int[st_count];
            for (int i = 0; i < st_count & st.hasMoreElements(); i++) {
                versions[i] = Integer.parseInt((String) st.nextElement());
            }
            System.out.println(versions);
        }
    }

    private void checkSession(HttpServletRequest request) {
        // セッションを取得します
        HttpSession session = request.getSession(false);

        if (session == null) { // セッションが存在しない場合
            session = request.getSession(true);
            session.setAttribute("last-access", new Date());
            System.out.println("Create session : " + session.getId());
        }
        Date date = (Date) session.getAttribute("last-access");
        System.out.println("Last-Access:" + date);
    }
}