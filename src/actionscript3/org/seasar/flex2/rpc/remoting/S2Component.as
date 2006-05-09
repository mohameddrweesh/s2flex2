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
 package org.seasar.flex2.rpc.remoting {
	
	import flash.events.Event;
	import flash.net.NetConnection;
	import flash.net.ObjectEncoding;
	import flash.net.Responder;
	import flash.utils.Proxy;
	import flash.utils.flash_proxy;
	
	import mx.core.IMXMLObject;
	import mx.managers.CursorManager;
	import mx.messaging.config.ServerConfig;
	import mx.rpc.AbstractService;
	import mx.rpc.Fault;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	/**
	* 
	*/
	use namespace flash_proxy;
	/**
	 * S2Component Invoker
	 * @author nod
	 * @author sato-shi
	 */
	public dynamic class S2Component extends AbstractService implements IMXMLObject
	
	{

		/** @private
		*/
		[Event("fault")]
		/** @private
		*/
		[Event("result")]

	    private var _con:NetConnection;
	    
	    [Inspectable(type="String")]
		public var gatewayUrl:String;
   
   		[Inspectable(type="Boolean",defaultValue="true")]
   		public var showBusyCursor:Boolean;
   		
   		/* not implements */
   		[Inspectable(enumeration="single,last,multiple",defaultValue="single",category="General")]
   		public var concurrency:String;

   		
		public function S2Component(){
			super(null);
		}
		
		public var id:String;
		
		private var document:Object;
		 
		public function initialized(document:Object,id:String):void
		{
			this.document=document;
		}
		 
		 private function initConnection():void{
			_con = new NetConnection();
			_con.objectEncoding = ObjectEncoding.AMF3;
			_con.addHeader("DescribeService", false, 0);
			var config:XML = ServerConfig.xml;
			if(this.gatewayUrl == null){	
				this.gatewayUrl=config.channels.channel.(@id==config..destination.(@id==this.destination).channels.channel.@ref).endpoint.@uri.toString();
			}
			_con.connect(this.gatewayUrl);	
		}

	    flash_proxy override function callProperty(methodName:*, ...args):* {
			 args.unshift(methodName);
			 return remoteCall.apply(null,args);
    	}

		private function remoteCall(methodName:Object, ...rest):void {
			if(_con==null){
				initConnection();
			}
	        if (this.showBusyCursor)
	        {
	            CursorManager.setBusyCursor();
	        }
	        
			var callMethod:String =this.destination +"." +methodName; 
			var responder:Responder = new Responder(this.onResult,this.onFault);
			
			 if(rest.length>0){			
				rest.unshift(callMethod,responder);
				_con.call.apply(_con,rest);
			}else{
				_con.call(callMethod,responder);
			}			
	    }
		
	    public function onResult(result:*):void{
	    	 if (this.showBusyCursor)
            {
                CursorManager.removeBusyCursor();
            }
	    	var resultEvent:ResultEvent=new ResultEvent(result,null,null);
	    	dispatchEvent(resultEvent);
	    }
	    
	    public function onFault(result:*):void{
	    	if (this.showBusyCursor)
            {
                CursorManager.removeBusyCursor();
            }
            var fault:Fault = new Fault(result.code,result.description,result.details);
//fault,null,null);
	    	var faultEvent:FaultEvent = new FaultEvent(result.code,false,false,fault);
	    	dispatchEvent(faultEvent);	
	    }
	}
}
