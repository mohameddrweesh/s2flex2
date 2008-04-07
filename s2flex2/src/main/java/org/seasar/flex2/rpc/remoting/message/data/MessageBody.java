/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.flex2.rpc.remoting.message.data;

/**
 * AMFのMessageボディクラスです。
 * @author e1.arkw
 * @author nod
 * {@link Message}のボディ部になります
 */
public class MessageBody {

    private final static Object[] EMPTY_ARGS = new Object[0];

    private Object[] args;

    private Object data;

    private String response;

    private String serviceMethodName;

    private String serviceName;

    private String target;

    public Object[] getArgs() {
        if (args == null) {
            setupData();
        }
        return args;
    }

    public Object getData() {
        return data;
    }

    public String getResponse() {
        return response;
    }

    public String getServiceMethodName() {
        if (serviceMethodName == null) {
            setupTarget();
        }
        return serviceMethodName;
    }

    public String getServiceName() {
        if (serviceName == null) {
            setupTarget();
        }
        return serviceName;
    }

    public String getTarget() {
        return target;
    }

    public final String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append("target=");
        buf.append(target);
        buf.append(",response=");
        buf.append(response);
        buf.append(",data=");
        buf.append(data);
        return buf.toString();
    }

    protected void setupData() {
        if ((data != null) && (data instanceof Object[])) {
            args = (Object[]) data;
        } else {
            args = EMPTY_ARGS;
        }
    }

    protected void setupTarget() {
        final int dotIndex = target.lastIndexOf('.');
        if (dotIndex > 0) {
            serviceName = target.substring(0, dotIndex);
            serviceMethodName = target.substring(dotIndex + 1);
        }
    }

    public void setArgs(final Object[] args) {
        this.args = args;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public void setServiceMethodName(final String serviceMethodName) {
        this.serviceMethodName = serviceMethodName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public void setResponse(final String response) {
        this.response = response;
    }

    public void setTarget(final String target) {
        this.target = target;
    }
}