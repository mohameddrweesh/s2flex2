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
	import mx.utils.Delegate;
	
	import org.seasar.flex2.net.AMF0Connection;

	/**
	 * 
	 * @author USER
	 * 
	 */
	dynamic public class SimpleAMF extends RemoteObject
	{

		[DefaultTriggerEvent("result")]

		// IMPORTANT: the Events below aren't used.  They shouldn't be asdoc'd or
		// otherwise documented for now.  They are just here for metadata for Brady.
		/** @private
		*/
		[Event("fault")]
		/** @private
		*/
		[Event("result")]

	    private var _con:AMF0Connection;
	    
	    [Inspectable(type="String")]
		public var gatewayUrl:String;
   
		public function SimpleAMF(){
			super();
		}
		
		private function initConnection(){
			_con = new AMF0Connection();
			_con.objectEncoding = ObjectEncoding.AMF0;
			_con.addHeader("DescribeService", false, 0);
			_con.connect(this.gatewayUrl);
			
		}

		public  function remoteCall(methodName:Object, ...args):void {
			if(_con==null){
				initConnection();
			}
			
			var callMethod:String =this.destination +"." +methodName; 

			var responder:Responder = new Responder(this.onResult,this.onFault);

			trace("call is " + callMethod);
			trace("gateway is " + gatewayUrl);
						
			if(args.length>0){			
				_con.call(callMethod,responder,args);
			}else{
				_con.call(callMethod,responder);
			}
			
	    }
	    public function onResult(resultData:Object){
	    	var resultEvent:ResultEvent=new ResultEvent(resultData,null,null);
	    	
	    	dispatchEvent(resultEvent);
	    }
	    public function onFault(faultData:Object){
	    	var faultEvent:FaultEvent = new FaultEvent(faultData,null,null);
	    	dispatchEvent(faultEvent);
	    	
	    }
	}
}
