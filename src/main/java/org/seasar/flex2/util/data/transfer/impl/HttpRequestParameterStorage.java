package org.seasar.flex2.util.data.transfer.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.seasar.flex2.util.data.transfer.Storage;



public class HttpRequestParameterStorage implements Storage {
    
    private final static String REQUEST = "request";

    private final HttpServletRequest request;

    public HttpRequestParameterStorage(HttpServletRequest request) {
        this.request = request;
    }

    public Object getProperty(String name) {
        return request.getParameter(name);
    }

    public Enumeration getPropertyNames() {

        return request.getParameterNames();
    }

    public void setProperty(String name, Object value) {
    }

    public String getName() {
        return REQUEST;
    }
}
