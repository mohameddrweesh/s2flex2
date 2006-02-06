package org.seasar.flex2.net{
	import flash.net.NetConnection;
	import flash.net.ObjectEncoding;
	
	public class AMF0Connection extends NetConnection{
		public function AMF0Connection(){
			super();
			objectEncoding = ObjectEncoding.AMF0;
		}
		public function AppendToGatewayUrl(append:String):void{
		}
		
		public function AddHeader():void{
		}
		
		public function ReplaceGatewayUrl():void{	
		}
	}
}

