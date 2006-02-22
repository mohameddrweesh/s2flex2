package org.seasar.flex2.rpc.remoting {
	
	import flash.events.Event;
	import flash.net.NetConnection;
	import flash.net.ObjectEncoding;
	import flash.net.Responder;
	import flash.util.trace;
	
	import mx.core.IMXMLObject;
	import mx.events.EventDispatcher;
	import mx.managers.CursorManager;
	import mx.messaging.config.ServerConfig;
	import mx.rpc.AbstractService;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	import mx.utils.ObjectUtil;

	/**
	 * S2Component Invoker
	 * @author nod
	 * @author sato-shi
	 */
	dynamic public class S2Component extends AbstractService implements IMXMLObject
	
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
   		
   		/** not implements */
   		[Inspectable(enumeration="single,last,multiple",defaultValue="single",category="General")]
   		public var concurrency:String;

/*   		
		public function S2Component(){
			super();
		}
*/		
		public var id:String;
		
		/**
		 * @private
		 */
		 private var document:Object;
		 
		 public function initialized(document:Object,id:String):void
		 {
		 	this.document=document;
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
		
	    public function onResult(resultData:Object){
	    	 if (this.showBusyCursor)
            {
                CursorManager.removeBusyCursor();
            }
	    	var resultEvent:ResultEvent=new ResultEvent(resultData,null,null);
	    	dispatchEvent(resultEvent);
	    }
	    
	    public function onFault(faultData:Object){
	    	if (this.showBusyCursor)
            {
                CursorManager.removeBusyCursor();
            }
	    	var faultEvent:FaultEvent = new FaultEvent(faultData,null,null);
	    	dispatchEvent(faultEvent);	
	    }
	}
}
