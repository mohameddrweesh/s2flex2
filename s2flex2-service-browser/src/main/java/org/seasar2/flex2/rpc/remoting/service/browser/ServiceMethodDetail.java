package org.seasar2.flex2.rpc.remoting.service.browser;

public class ServiceMethodDetail {
    private String name;

    private String[] arguments;

    private String returnType;

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
