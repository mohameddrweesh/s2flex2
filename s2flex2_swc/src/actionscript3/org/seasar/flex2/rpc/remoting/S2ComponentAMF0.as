package org.seasar.flex2.rpc.remoting {
	import flash.net.ObjectEncoding;
	import flash.net.NetConnection;
	import flash.util.Proxy;
	import flash.util.flash_proxy;
	import org.seasar.flex2.net.NetConnection2;
	
	import mx.rpc.AbstractService;	
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	import mx.core.IMXMLObject;
	import flash.util.describeType;
	//import mx.rpc.remoting.RemoteObject;

	dynamic public class S2ComponentAMF0 /* extends mx.rpc.AbstractService*/ extends RemoteObject  
	{
		private var data:XML;
		/*
		[Inspectable(enumeration="ukeke,mokeke,okeke",defaultValue="ukeke",category ="S2Flex2",type="String")]
		public var hogehoge:String;
		[Inspectable]
		
	    [ArrayElementType("String")]
	    public var newStringProperty:Array;
	    */
	    public var _con:NetConnection2;
	    
	    [Inspectable(type="String")]
	    public var operationDump:String;
	    
	    [Inspectable(type="String")]
	    public var destinationDump:String;
	    [Inspectable(type="String")]
		public var gatewayUrl:String;
/*
	    private var _gatewayUrl:String;
	    
	    [Inspectable(type="String")]	    
	    public function get gatewayUrl():String{
	    	return _gatewayUrl;
	    }
  	    [Inspectable(type="String")]	    
	    public function set endpointUrl(value:String):Void{
	    	this._gatewayUrl=value;
	    }
	*/    
		public function S2ComponentAMF0(){
				//super.destination;
				//fault = super.fault;
				//result = super.result;
				//super.operations;
				super();	
				_con = new NetConnection2();
				_con.objectEncoding = ObjectEncoding.AMF0;
				_con.connect(this.gatewayUrl);
						
				//_con.connect(super.destination);
				//_con.connect(gatewayURL);
				//var r:Responder = new Responder(TestBeanResult);
				
		}
		public function GetDaze():Object{
		}
		/*
		flash_proxy override function getProperty(variableName:Object):Object {
			return null;
		}
		*/
		/*
		flash_proxy override function getProperty(methodName:Object, ...args):Object {
			var op:String = mx.utils.ObjectUtil.toString( super.operations );
			operationDump=op;
			destinationDump =mx.utils.ObjectUtil.toString( super.destination );
				
	      return super.getOperation(methodName).send(args);
	    }*/
		
		
//		flash_proxy override function callProperty(methodName:Object, ...args):Object {
		public  function remoteCall(methodName:Object, ...args):Object {
			var cs:Namespace =data.namespaces("cs");
			operationDump = mx.utils.ObjectUtil.toString( super.operations );
			destinationDump =mx.utils.ObjectUtil.toString( super.destination );
	      return super.getOperation(methodName).send(args);
	    }
	    
	}
}
//flash_proxy override function callProperty(methodName:Object, ...args):Object {
