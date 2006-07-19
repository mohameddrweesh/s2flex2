package org.seasar2.flex2.rpc.remoting.service.browser;

import java.util.List;

public class ServiceDetail {
    private String name;

    private String className;

    private String[] interfaces;

    private List methodDetails;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    public List getMethodDetails() {
        return methodDetails;
    }

    public void setMethodDetails(List methodDetails) {
        this.methodDetails = methodDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
