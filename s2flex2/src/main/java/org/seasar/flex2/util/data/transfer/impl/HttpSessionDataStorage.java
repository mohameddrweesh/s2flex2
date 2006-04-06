package org.seasar.flex2.util.data.transfer.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.seasar.flex2.util.data.transfer.Storage;



public class HttpSessionDataStorage implements Storage {
    
    private final static String SESSION = "session";

    private final HttpSession session;

    public HttpSessionDataStorage(HttpSession session) {
        this.session = session;
    }

    public Object getProperty(String name) {
        return session.getAttribute(name);
    }

    public Enumeration getPropertyNames() {

        return session.getAttributeNames();
    }

    public void setProperty(String name, Object value) {
        session.setAttribute(name, value);
    }

    public String getName() {
        return SESSION;
    }
}
