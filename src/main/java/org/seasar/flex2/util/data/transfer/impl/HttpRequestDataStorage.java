package org.seasar.flex2.util.data.transfer.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.seasar.flex2.util.data.transfer.Storage;



public class HttpRequestDataStorage implements Storage {
    
    private final static String REQUEST = "request";

    private final HttpServletRequest request;

    public HttpRequestDataStorage(HttpServletRequest request) {
        this.request = request;
    }

    public Object getProperty(String name) {
        return request.getAttribute(name);
    }

    public Enumeration getPropertyNames() {

        return request.getAttributeNames();
    }

    public void setProperty(String name, Object value) {
        request.setAttribute(name, value);
    }

    public String getName() {
        return REQUEST;
    }
}
