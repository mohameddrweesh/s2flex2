package org.seasar.flex2.rpc.remoting {
	
	import flash.events.Event;
	import flash.net.NetConnection;
	import flash.net.ObjectEncoding;
	import flash.net.Responder;
	import flash.util.trace;
	import mx.events.EventDispatcher;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	import mx.utils.ObjectUtil;
	import mx.messaging.config.ServerConfig;
	import mx.logging.targets.TraceTarget;
	import mx.rpc.AbstractService;

	/**
	 * 
	 * @author nod
	 * @author sato-shi
	 */
//	dynamic public class SimpleAMF extends RemoteObject
	// AbstractServiceだとshowBusyCursorができない..
	dynamic public class SimpleAMF extends AbstractService
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
   
		public function SimpleAMF(){
			super();
		}
		
		private function initConnection(){
			_con = new NetConnection();
			_con.objectEncoding = ObjectEncoding.AMF0;
			_con.addHeader("DescribeService", false, 0);
			var config:XML = ServerConfig.xml;
			if(this.gatewayUrl == null){	
				this.gatewayUrl=config.channels.channel.(@id==config..destination.(@id==this.destination).channels.channel.@ref).endpoint.@uri.toString();
			}
			_con.connect(this.gatewayUrl);
			
		}

		public  function remoteCall(methodName:Object, ...rest):void {
			if(_con==null){
				initConnection();
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
	    
	    public function onResult(resultData:Object){
	    	var resultEvent:ResultEvent=new ResultEvent(resultData,null,null);
	    	dispatchEvent(resultEvent);
	    }
	    
	    public function onFault(faultData:Event){
	    	var faultEvent:FaultEvent = new FaultEvent(faultData,null,null);
	    	dispatchEvent(faultEvent);	
	    }
	}
}
