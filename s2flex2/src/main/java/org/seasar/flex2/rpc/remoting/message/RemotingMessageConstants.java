/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.rpc.remoting.message;

/**
 * <h4>RemotingMessageConstants</h4>
 * RemotingMessageで利用される定数を定義するインターフェース
 * @author e1.arkw
 * @author nod
 *
 */
public interface RemotingMessageConstants {

    static final String APPEND_TO_GATEWAYURL = "AppendToGatewayUrl";
    
    static final String CREDENTIALS_PASSWORD = "credentialsPassword";

    static final String CREDENTIALS_USERNAME = "credentialsUsername";

    static final String REMOTE_CREDENTIALS_PASSWORD = "remoteCredentialsPassword";

    static final String REMOTE_CREDENTIALS_USERNAME = "remoteCredentialsUsername";
    
    /** 認証用のヘッダ名称を示す定数 */
    static final String HEADER_NAME_CREDENTINALS= "Credentials";
    static final String USERID="userid";
    static final String PASSWORD="password";
}
