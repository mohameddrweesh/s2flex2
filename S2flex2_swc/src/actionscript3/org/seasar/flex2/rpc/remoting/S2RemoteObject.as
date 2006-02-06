package org.seasar.flex2.rpc.remoting {
	import flash.net.ObjectEncoding;
	import flash.net.NetConnection;
	import flash.util.Proxy;
	import mx.utils.ObjectUtil;
	import org.seasar.flex2.net.NetConnection2;
	import mx.rpc.remoting.Operation;
	import mx.rpc.AbstractService;	
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.mxml.RemoteObject;
	import mx.core.IMXMLObject;


	dynamic public class S2RemoteObject /*extends mx.rpc.AbstractService*/extends RemoteObject implements IMXMLObject
	{
		[DefaultTriggerEvent("result")]

		[ResultEvent("fault")]
		[FaultEvent("result")]

		[Inspectable(enumeration="hoge1,hoge2,honge3",defaultValue="hoge2",category ="General",type="String")]
		public var hogehoge:String;
	
		[Inspectable(type="NetConnection",categoty="S2Flex")]
		private var _conn:NetConnection2;
			
	    [ArrayElementType("String")]
	    public var newStringProperty:Array;
	    
	    /*
	    public function getDestination():String{
	    	 ObjectUtil.toString( ResultEvent(event).result );
	    }*/
		public function S2RemoteObject(){
			NetConnection.defaultObjectEncoding=ObjectEncoding.AMF0;
			_conn=new NetConnection2();
			_conn.connect
			
				//super.destination;
				//fault = super.fault;
				//result = super.result;
				
				//super.operations;
				
		}
		
		
	}
}
//flash_proxy override function callProperty(methodName:Object, ...args):Object {
