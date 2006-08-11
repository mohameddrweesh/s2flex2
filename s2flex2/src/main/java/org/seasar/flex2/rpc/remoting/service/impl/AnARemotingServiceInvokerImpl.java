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
package org.seasar.flex2.rpc.remoting.service.impl;

public class AnARemotingServiceInvokerImpl extends
        AbstractRemotingServiceInvokerImpl {
    
    public static final int AllAllowBehavior = 1;

    public static final int AllDenyBehavior = 0;
    
    private int defaultBehavior;
    
    //private AuthenticationContextResolver authenticationContextResolver;

    public Object invoke(String serviceName, String methodName, Object[] args)
            throws Throwable {

        final Object service = remotingServiceLocator.getService(serviceName);
        /*
        //applicationContext を探す
        AuthenticationContext context = authenticationContextResolver.resolve(serviceName,methodName);
        if( context != null ){
            if( !context.isAuthenticated() ){
                Principal principal = context.getUserPrincipal();
                if(principal == null ){
                    //例外
                }
            }
            if( context.isUserInRole()){
                
            }
        } else {
        //無い場合
            switch( defaultBehavior ){
                case AllDenyBehavior:
                    //例外
                    break;
                case AllAllowBehavior:
                default:
            }
        }
        */
        return invokeServiceMethod(service, methodName, args);
        
    }
}