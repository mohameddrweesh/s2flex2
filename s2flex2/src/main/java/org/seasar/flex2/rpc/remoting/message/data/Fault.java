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

import java.util.Map;

/**
 * RemotingMessageを処理している際に例外が発生したときに生成されるクラスです
 * @author arkw
 * @author nod
 */
public class Fault {

    private static final String faultCode = "Server.Processing";

    /**
     * @deprecated Use {@link #faultCode} instead
     */
    private static final String code = faultCode;
    /**
     *  Text description of the fault.
     *  エラー、例外が発生したときの内容.例外のgetMessage相当
     */
    private String faultString;
    /**
     * Additional details describing the fault.
     * エラー、例外が発生したときの詳細情報. 例外のstackTrace相当
     */
    private String faultDetail;

    private final String level = "error";

    private String type;
    /**
     * エラー、例外の原因の発生源
     */
    private Object rootCause;
    private Map extendedData;

    /**
     * @return Returns the rootCause.
     */
    public Object getRootCause() {
        return rootCause;
    }

    /**
     * @param rootCause The rootCause to set.
     */
    public void setRootCause(Object rootCause) {
        this.rootCause = rootCause;
    }

    /**
     * @deprecated Use {@link #getFaultCode()} instead
     */
    public String getCode() {
        return getFaultCode();
    }

    public String getFaultCode() {
        return faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public String getFaultDetail() {
        return faultDetail;
    }

    public String getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public void setFaultString(final String description) {
        this.faultString = description;
    }

    public void setFaultDetail(final String details) {
        this.faultDetail = details;
    }

    public void setType(final String type) {
        this.type = type;
    }
    
    /**
     * @deprecated Use {@link #getFaultString()} instead
     * @return fault概要
     */
    public String getDescription() {
        return getFaultString();
    }

    /**
     * @deprecated Use {@link #getFaultDetail()} instead
     * @return faultの詳細
     */
    public String getDetails() {
        return getFaultDetail();
    } 
    /**
     * @return Returns the extendedData.
     */
    public Map getExtendedData() {
        return extendedData;
    }

    /**
     * @param extendedData The extendedData to set.
     */
    public void setExtendedData(Map extendedData) {
        this.extendedData = extendedData;
    }
}