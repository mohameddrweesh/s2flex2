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
 *
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
    import org.seasar.flex2.utils.ApplicationUtil;
    import org.seasar.flex2.utils.HttpUtil;
    
    use namespace flash_proxy;
    
    //--------------------------------------
    // icon
    //--------------------------------------
    [IconFile("S2Component.png")]
    //--------------------------------------
    //  Events
    //--------------------------------------
    /** 
     *
     *  The fault event is dispatched when a service call fails and isn't handled by the Operation itself.
    　　  *   <br />
     *  fault イベントは、サービス(サーバロジック)を実行した際に、失敗、または例外が発生したときに送出されます。
     *  @eventType mx.rpc.events.FaultEvent.FAULT
     *  @tiptext fault event
     */
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]
    /** 
     * The result event is dispatched when a service call successfully returns and isn't handled by the Operation itself.
     *  <br />
	 *  result イベントは、サーバロジックを実行し正常に処理されたときに送出されます。
     *  @eventType mx.rpc.events.ResultEvent.RESULT
     *  @tiptext result event
     */
    [Event(name="result", type="mx.rpc.events.ResultEvent")]
    /** 
	　*　　IOErrorはNetwork接続時に入出力エラーが発生したときに送出されます。
     *  @eventType flash.events.IOErrorEvent.IO_ERROR
     *  @tiptext ioError event
     */
    [Event(name="ioError", type="flash.events.IOErrorEvent")]
    /** 

     *  @eventType flash.events.NetStatusEvent.NET_STATUS
     *  @tiptext netStatus event
     */
    [Event(name="netStatus",type="flash.events.NetStatusEvent")]
    /** 
     *  @eventType flash.events.SecurityErrorEvent.SECURITY_ERROR
     *  @tiptext securityError event
     */
    [Event(name="securityError",type="flash.events.SecurityErrorEvent")]
    
    /**
    * 
    * <p>S2Flex2Serviceは、S2Flex2のgatewayに接続する為のコンポーネントクラスです。<br />
    * S2Container(Seasar2)に登録されたコンポーネントにアクセスして、メソッド実行を可能にします。</p>
    * <!--
    * S2Flex2Service is ...(TBD)
    *  -->
    * <h4>Gatewayの指定</h4>
    * <p>このサービスの接続先であるGatewayを指定する方法は、３パターンあります。</p>
    * <ul>
    * <li>gatewayUrlプロパティで指定する方法</li>
    * <li>configType="url"で指定する方法</li>
    * <li>configType="flashvars"で指定する方法</li>
    * </ul>
    * <h5>gatewayUrlプロパティで指定する方法</h5>
    * <p>接続先の指定としてgatewayURLプロパティに指定する方法があります。<br/>
    * <code>&lt;s2:S2Flex2Service gatewayUrl="http://127.0.0.1:8080/flex/gateway" ...</code>
    * この方法を使うと明示的に接続先を指定することができますが、接続先が変更になると、再度コンパイルする必要があります。</p>
    * 
    * <h5>configType="url"で指定する方法</h5>
    * <p>接続先の設定としてデフォルト設定になっているのは、このurlで指定する方法です。
    * systemManager.loaderInfo.urlの値を使って処理します。
    * 例)
    *	http://127.0.0.1/test/test.swfだった場合は、
    *	http://127.0.0.1/test/gatewayとしてます。
    *	
    *	同じ階層のgatewayというURLをGatewayURLに指定します。</p>
    *  <p>Flex Builder 2 などでデバッグしているときは、flashvarsでdefaultGatewayを指定することで
    * 　接続することができるようになります。</p>
    *   <code>&lt;param name=&quot;FlashVars&quot; value=&quot;defaultGateway=http://127.0.0.1/flex2/gateway&quot; /&gt;</code>
    * <p />
    * <h5>configType=&quot;flashvars&quot;で指定する方法</h5>
    * <p>configType=&quot;flashvars&quot;を指定したときには、htmlファイルの中でflashvarsに<strong>defaultGateway</strong>を指定します。
    * サービス単位で接続先を変更するときには、destination名でgatewayUrlを指定します。
    * 以下は、addServiceの接続先を指定する例です。addSevrice意外のサービスは、defaultGatewayに接続する事になります。</p>
    * <code>&lt;param name=&quot;FlashVars&quot; value=&quot;addService="http://127.0.0.1/flexservice/gateway"&amp;&quot;defaultGateway=http://127.0.0.1/flex2/gateway&quot; /&gt;</code>
    * @mxml
    * 
    * <p><code>&lt;s2:S2Service&gt;</code>タグは、スーパークラスの属性に加えて以下の属性を持ちます。</p>
    * 
    * <pre>
    * &lt;s2:S2Flex2Service
    * <b>Properties</b>
    * gatewayUrl="null"
    * showBusyCursor="true|false"
    * id="<i>No default</i>"
    * fault="<i>No default</i>"
    * result="<i>No default</i>"
    * destination="<i>No default</i>"
    * configType="flashvars|xml|url"
    * /&gt;
    * </pre>
    * 
    * @example S2Flex2Serviceの使用例は以下の通りです。
    * <pre>
	* 	addService=  new S2Flex2Service("addService");
	* 	addService.initialized(this,"addService");
	* 
	* 	addService.destination="addService";
	* 	addService.addEventListener(ResultEvent.RESULT,onGetData);
	* 	addService.addEventListener(FaultEvent.FAULT,onFault);
 	*	addService.addEventListener(IOErrorEvent.IO_ERROR,failreProcess);
 	*	addService.addEventListener(IOErrorEvent.NETWORK_ERROR,failureProcess);
	*	addService.addEventListener(NetStatusEvent.NET_STATUS,failureProcess);
	*	addService.addEventListener(SecurityErrorEvent.SECURITY_ERROR,failureProcess);
	*	addService.addEventListener(FaultEvent.FAULT, failurProcess);
	*	addService.setCredentials("user_id","password");	//Authenication
	* 	addService.getAddDtoData();							//service invoke
    * </pre>
    * 
    */  
    public dynamic class S2Flex2Service extends AbstractService implements IMXMLObject {
        
        /** サーバ接続する際のURLです。 AMFのGatewayを指定します。 */
        [Inspectable(type="String")]
        public var gatewayUrl:String;
   
   		/** サーバロジックを呼び出している間、カーソルをBusyCursorにするかどうかを決定します。 デフォルトはtrueです。*/
        [Inspectable(type="Boolean",defaultValue="true")]
        public var showBusyCursor:Boolean;
       
        /* not implements */
        /** このプロパティについては、実装には反映されていません。(TBD)<br/>将来のリリースで改善される可能性はあります。 */
        [Inspectable(enumeration="single,last,multiple",defaultValue="single",category="General")]
        public var concurrency:String;
        
		/** mxml内部ドキュメント内部で管理されるこの S2Flex2Sericeインスタンスを特定するID です。 */
        [Inspectable(type="String")]
        public var id:String;
        
        /** サーバ接続の際に利用されるNetConnectionのインスタンスです。*/
        protected var _con:NetConnection;
        
        /**
         *  @private
         */
        private var document:UIComponent;
        
        /**
	     * 　認証が必要な外部接続サービスを呼び出す際に指定する、ユーザ名です。
         *  @private
         */
        private var remoteCredentialsUsername:String;
        
        /**
		　*   認証が必要な外部接続サービスを呼び出す際に指定する、パスワードです。
		　*  @private
		　*/
        private var remoteCredentialsPassword:String;

        /**
		　* 認証が必要なサービスを呼び出す際に指定するユーザ名です。
         *  @private
         */
        private var credentialsUsername:String;

        /**
		　* 認証が必要なサービスを呼び出す際に指定するパスワードです。
         *  @private
         */
        private var credentialsPassword:String;

        /**
         *  @private
         */        
        private var _opResponderArray:Object;
        
        [Inspectable(enumeration="flashvars,xml,url",defaultValue="url",category="General")]
        public var configType:String;

         /**
         * コンストラクタ.<br/>
         * <p>destinationをセットしたうえでResponderの配列を初期化します。</p>
         * 
         * @param destination サービスの宛先.呼び出すコンポーネントの名称を指定します。
         * 
         */
        public function S2Flex2Service(destination:String=null){
            super(destination);
            this._opResponderArray=new Array();
        }

		/**
		 * mxmlファイル内でS2Flex2Serviceをタグとして設定したときにMXMLコンパイラ(mxmlc)によって自動的に呼び出されます。<br/>
		 * <!--ActionScriptでインスタンスを生成した場合は、このメソッドを呼び出す事で、GatewayURLを自動的にセットすることが可能になります。-->
		 * このクラスを ActionScript で作成し、検証で機能させるには、このメソッドを呼び出して、MXML ドキュメントと S2Flex2Service の id を渡す必要があります。
		 * 正しく指定しないと、Gatewayの指定が正しく行われない可能性があります。
		 * 
		 * @param document このクラスを利用しているmxml documents
		 * @param id S2Flex2Serviceのインスタンス
		 * 
		 */ 
        public function initialized(document:Object,id:String):void{
            this.document = document as UIComponent;
        }

        /**
        * 認証が必要なサービスを呼び出す際に必要な認証情報をセットします。
        * 
        * @param username 認証に利用するユーザID
        * @param password 認証に利用するパスワード
        */
        public override function setCredentials(username:String, password:String):void{
            this.credentialsUsername = username;
            this.credentialsPassword = password;
        }

        /**
        * 認証が必要な外部サービスを呼び出す際に必要な認証情報をセットします。
        * (TBD.)
        * <br/>
        * <small>このメソッドは呼び出しても、S2Flex2 1.0.xでは認識しません。</small>
        * @param username 認証に利用するユーザID
        * @param password 認証に利用するパスワード
        */         
        public override function setRemoteCredentials(remoteUsername:String, remotePassword:String):void{
            this.remoteCredentialsUsername = remoteUsername;
            this.remoteCredentialsPassword = remotePassword;
        }
		
		/**
		 * 呼び出したリモートサービスが正常に終了したときに呼ばれます
		 * @param operation 呼び出したサービスのメソッド名
		 * @param result 呼び出したサービスの結果
		 * 
		 */ 
        public function onResult(operation:String,result:*):void{
            hiddenBusyCursor();
            var responder:RelayResponder=this._opResponderArray[operation];
            
            var resultEvent:ResultEvent=new ResultEvent("result",false,false,result,responder.asyncToken,responder.asyncToken.message);
            dispatchEvent(resultEvent);
        }
        
		/**
		 * 呼び出したリモートサービスが何らかのエラーを返してきたときに呼ばれます
		 * @param operation 呼び出したサービスのメソッド名
		 * @param result 呼び出したエラー結果
		 */ 
        public function onFault(operation:String,result:Object):void{
        	hiddenBusyCursor();
            var responder:RelayResponder=this._opResponderArray[operation];
            var fault:Fault = new Fault(result.faultCode,result.faultString,result.faultDetail);
            fault.rootCause=result.rootCause;	//2008.04. rootCause added.
            var faultEvent:FaultEvent = new FaultEvent("fault",false,false,fault,responder.asyncToken,responder.asyncToken.message);
            dispatchEvent(faultEvent);
        }

        flash_proxy override function callProperty(methodName:*, ...args):*{
        	var operation:RpcOperation= getProperty(methodName);
        	if(operation){
        		args.unshift(operation.name);
        	}else
        	{
             args.unshift(methodName);
         	}
             return remoteCall.apply(null,args);
        }
        
        /**
        * Serviceより呼び出すOperationをセットします。
        * @param name メソッド名
        * @param value メソッドに対応したRPCOperation
        */
        flash_proxy override function setProperty(name:*,value:*):void{
            this.operations[name]=value;
        }
        
        /**
        * 指定されたnameにあわせたOperationを返します。
        * @return RPCOperation
        * @ignore
        */ 
        flash_proxy override function getProperty(name:*):*{
            if(this.operations[name]==null){
                this.operations[name]=new RpcOperation(this,name);
            }
            return this.operations[name];
        }

        /**
         * NetConnectionのインスタンスを生成します。
         * 
         */    
        protected function createConnection():void{
            _con = new org.seasar.flex2.net.NetConnection();
            _con.objectEncoding = ObjectEncoding.AMF3;
            _con.addHeader("DescribeService", false, 0);    
        }

        /**
         * NetConnectionの初期化を行います。
         * 
         */
        protected function initConnection():void{
            createConnection();
            resolveGatewayUrl();
            configureListeners();
            processConnect();
        }

       /**
         * NetConnectionにEventListenerの設定を行います。
         * 
        */
        protected function configureListeners():void {
            _con.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
            _con.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
            _con.addEventListener(flash.events.IOErrorEvent.IO_ERROR , ioErrorHandler);
        }
        
         /**
         * gatewayUrlとして指定された場所に接続を行います。
         * @private
         */       
        private function processConnect():void{
            _con.connect(this.gatewayUrl);
        }
        
        /**
         * 指定されたサービスを呼び出します。
         * @param methodName 呼び出すメソッド名
         * @param rest メソッドを呼び出すときのパラメータ
         * @private
         */        
        private function remoteCall(methodName:String, ...rest):AsyncToken {
            setupConnection();
            setupCredentials();
            setupRemoteCredentials();
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
        /**
        * busyCursorをもとのCursorに戻します。
        * @private
        */ 
        private function hiddenBusyCursor():void{
            if (this.showBusyCursor){
                CursorManager.removeBusyCursor();
            }
        }
        /**
        * @private
        */ 
        private function netStatusHandler(event:NetStatusEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
        }
        
        /**
        * @private
        */         
        private function securityErrorHandler(event:SecurityErrorEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
        }
          
        /**
        * @private
        */         
        private function ioErrorHandler(event:IOErrorEvent):void {
            hiddenBusyCursor();
            dispatchEvent(event);
        }
        
        /**
        * @private
        */
        private function setupConnection():void{
            if(_con==null){
                initConnection();
            }           
        }
        /**
        * @private
        */
        private function setupCredentials():void {
            if( credentialsUsername != null ){
                _con.addHeader(
                    S2Flex2ServiceConstants.CREDENTIALS_USERNAME,
                    true,
                    credentialsUsername
                );
                _con.addHeader(
                    S2Flex2ServiceConstants.CREDENTIALS_PASSWORD,
                    true,
                    credentialsPassword
                );
                credentialsUsername = null;
                credentialsPassword = null;
            }
        }
        /**
        * @private
        */
        private function setupRemoteCredentials():void {
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
        /**
        * @private
        */
        private function setupCursor():void{
            if (this.showBusyCursor){
                CursorManager.setBusyCursor();
            }
        }
        /**
        * Serviceを呼び出す際のGatewayUrlを解決します。<br>
        * <ul>
        * <li>gatewayURLが指定されているときは、指定されたURLを利用します。</li>
        * <li>configTypeが"flashvars"として指定されておりdestinationがnullでないときには、destination毎のURLを利用します。</li>
        * <li>configTypeがflashvars以外のときには、swfファイルと同一階層にあるgatewayを利用します。</li>
        * </ul>
        * @private
        */
        private function resolveGatewayUrl():void{
            if(this.gatewayUrl == null ){
                if( this.configType == "flashvars" && this.destination != null){
                    
                    var gatewayUrl_:String = ApplicationUtil.getParameterValue( this.destination );                    
                    //destination単位でURLが指定されていないときには、defaultGatewayを指定する。
                    if( gatewayUrl_ == null ){
                    	gatewayUrl_ = ApplicationUtil.getParameterValue( "defaultGateway" );
                    }
                    this.gatewayUrl = HttpUtil.getUri(gatewayUrl_);
                    
                } else if(this.configType == "xml"){
                    //It has not implemented yet. 
                } else {
                    resolveDefaultGatewayUrl();
                }
            }
        }
        /**
        * gatewayのURLを実行された環境にあわせて解決してセットします。
        * swfのURLがHTTP/HTTPS以外のときには、defaultGatewayの値を使用します。
        * @private
        */
        private function resolveDefaultGatewayUrl():void{
            const url:String = document.systemManager.loaderInfo.url;
            var gatewayUrl_:String = null;
            if( URLUtil.isHttpURL(url) || URLUtil.isHttpsURL(url)){
                const lashSlash:int = url.lastIndexOf("/");
                gatewayUrl_ = url.substring(0, lashSlash+1 ) + "gateway";
            } else {
                gatewayUrl_ = ApplicationUtil.getParameterValue( "defaultGateway" );
            }
            
            this.gatewayUrl = HttpUtil.getUri(gatewayUrl_);
        }
    }
}