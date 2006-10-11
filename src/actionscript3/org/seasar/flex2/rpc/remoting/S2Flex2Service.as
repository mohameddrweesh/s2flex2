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
 * @ignore
 */
package org.seasar.flex2.rpc.remoting {

    import flash.events.IOErrorEvent;
    import flash.events.NetStatusEvent;
    import flash.events.SecurityErrorEvent;
    import flash.net.ObjectEncoding;
    import flash.net.Responder;
    import flash.utils.flash_proxy;
    
    import mx.core.Application;
    import mx.core.IMXMLObject;
    import mx.core.UIComponent;
    import mx.managers.CursorManager;
    import mx.messaging.config.ServerConfig;
    import mx.rpc.AbstractService;
    import mx.rpc.AsyncToken;
    import mx.rpc.Fault;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.utils.URLUtil;
    
    import org.seasar.flex2.net.NetConnection;
    import org.seasar.flex2.rpc.RelayResponder;
    import org.seasar.flex2.rpc.RpcOperation;
    
    use namespace flash_proxy;
	// events -------------------------------------------------------------------
    /** @private*/
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]    
    /** @private*/
    [Event(name="result", type="mx.rpc.events.ResultEvent")]
    [Event(name="ioError", type="flash.events.IOErrorEvent")]
    [Event(name="netStatus",type="flash.events.NetStatusEvent")]
    [Event(name="securityError",type="flash.events.SecurityErrorEvent")]
    // icon----------------------------------------------------------------------
    [IconFile("S2Component.png")]
    
    /**
    * S2Flex2Serviceは、S2Flex2のgatewayに接続する為のコンポーネントクラスです。
    * S2Flex2Service is ...
    */  
    public dynamic class S2Flex2Service extends AbstractService implements IMXMLObject {
        
        [Inspectable(type="String")]
        public var gatewayUrl:String;
   
        [Inspectable(type="Boolean",defaultValue="true")]
        public var showBusyCursor:Boolean;
       
        /* not implements */
        [Inspectable(enumeration="single,last,multiple",defaultValue="single",category="General")]
        public var concurrency:String;

        [Inspectable(type="String")]
        public var id:String;
        
        protected var _con:NetConnection;
        
        /* @private */
        private var document:UIComponent;
        
		/* @private remoteCredialsUserName  */
        private var remoteCredentialsUsername:String;
        
        /* @private remoteCredentialsPassword */
        private var remoteCredentialsPassword:String;
        
        public var _opResponderArray:Object;
        
        [Inspectable(enumeration="flashvars,xml,url",defaultValue="url",category="General")]
        public var configType:String;
                   
        public function S2Flex2Service(destination:String=null)
        {
            super(destination);
            this._opResponderArray=new Array();
        }
            
        public function initialized(document:Object,id:String):void
        {
            this.document = document as UIComponent;
        }
         
        public override function setRemoteCredentials(remoteUsername:String, remotePassword:String):void
        {
            this.remoteCredentialsUsername = remoteUsername;
            this.remoteCredentialsPassword = remotePassword;
        }
        
        public function onResult(operation:String,result:*):void
        {
            hiddenBusyCursor();
            var responder:RelayResponder=this._opResponderArray[operation];
            
            var resultEvent:ResultEvent=new ResultEvent("result",false,false,result,responder.asyncToken,responder.asyncToken.message);
            dispatchEvent(resultEvent);
        }
        
        public function onFault(operation:String,result:*):void
        {
            hiddenBusyCursor();
            
            var fault:Fault = new Fault(result.code,result.description,result.details);
            var faultEvent:FaultEvent = new FaultEvent("fault",false,false,fault,null,null);
            dispatchEvent(faultEvent);
        }
        
        flash_proxy override function callProperty(methodName:*, ...args):*
        {
             args.unshift(methodName);
             return remoteCall.apply(null,args);
        }
        
        /**
        * Serviceより呼び出すOperationをセットします。
        * @param name メソッド名
        * @param value メソッドに対応したRPCOperation
        **/
        flash_proxy override function setProperty(name:*,value:*):void{
        	this.operations[name]=value;
        }
        
        /**
        * 指定されたnameにあわせたOperationを返します。
        * @return RPCOperation
        * @ignore
        **/ 
        flash_proxy override function getProperty(name:*):*{
        	if(this.operations[name]==null){
        		this.operations[name]=new RpcOperation(this,name);
        	}
        	return this.operations[name];
        }
        
        protected function createConnection():void{
            _con = new org.seasar.flex2.net.NetConnection();
            _con.objectEncoding = ObjectEncoding.AMF3;
            _con.addHeader("DescribeService", false, 0);    
        }

        protected function initConnection():void{
            createConnection();
            resolveGatewayUrl();
            configureListeners();
            processConnect();
        }

        protected function configureListeners():void {
            _con.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
            _con.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            _con.addEventListener(flash.events.IOErrorEvent.IO_ERROR , ioErrorHandler);
        }
        
        private function processConnect():void{
            _con.connect(this.gatewayUrl);
        }
        
        private function remoteCall(methodName:String, ...rest):AsyncToken {
            setupConnection();
            setupCredential();
            setupCursor();
       
            var callMethod:String =this.destination +"." +methodName;
           	
            var responder:RelayResponder = new RelayResponder(methodName,this.onResult,this.onFault);
            this._opResponderArray[methodName]=responder;
            
            if(rest.length>0){
                rest.unshift(callMethod,responder);
                _con.call.apply(_con,rest);
            }else{
                _con.call(callMethod,responder);
            }
            return responder.asyncToken;
            
        }
        
        private function hiddenBusyCursor():void{
            if (this.showBusyCursor){
                CursorManager.removeBusyCursor();
            }
        }
        
        private function netStatusHandler(event:NetStatusEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
        }
        
        private function securityErrorHandler(event:SecurityErrorEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
          }
        
        private function ioErrorHandler(event:IOErrorEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
        }
        
        private function setupConnection():void{
            if(_con==null||!_con.connected){
                initConnection();
            }           
        }
        
        private function setupCredential():void{
            if( remoteCredentialsUsername != null ){
                _con.addHeader(
                    S2Flex2ServiceConstants.REMOTE_CREDENTIALS_USERNAME,
                    true,
                    remoteCredentialsUsername
                );
                _con.addHeader(
                    S2Flex2ServiceConstants.REMOTE_CREDENTIALS_PASSWORD,
                    true,
                    remoteCredentialsPassword
                );
                remoteCredentialsUsername = null;
                remoteCredentialsPassword = null;
            }
        }
        
        private function setupCursor():void{
            if (this.showBusyCursor){
                CursorManager.setBusyCursor();
            }
        }
        
        private function resolveGatewayUrl():void{
            if(this.gatewayUrl == null && this.destination != null ){
                if( this.configType == "flashvars"){
                    this.gatewayUrl = getApplicationParameterValue( this.destination );                    
                } else if(this.configType == "xml"){
                    //It has not implemented yet. 
                } else {
                    resulveDefualtGatewayUrl();
                }
            }
        }
        
        private function resulveDefualtGatewayUrl():void{
            const url:String = document.systemManager.loaderInfo.url;
            if( URLUtil.isHttpURL(url) || URLUtil.isHttpsURL(url)){
                const lashSlash:int = url.lastIndexOf("/");
                this.gatewayUrl = url.substring(0, lashSlash+1 ) + "/gateway";
            } else {
                this.gatewayUrl = getApplicationParameterValue( "defualtGateway" );
            }
        }
        
        private function getApplicationParameterValue( parameterName:String ):String{
            var parameterValue:String = null;
            
            if( document != null ){
                
                var application:Application = document.parentApplication as Application;
                if( application != null ){
                    parameterValue = application.parameters[ parameterName ];
                } else {
                    application = document.parentDocument as Application;
                    if( application != null ){
                        parameterValue = application.parameters[ parameterName ];
                    }
                }        
            }
            
            return parameterValue;
        }
    }
}