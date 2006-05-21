package org.seasar.flex2.net{
	import flash.net.NetConnection;
	import mx.controls.Alert;

		public class NetConnection extends flash.net.NetConnection{
		
			private var _originalUrl:String;
			
			public override function connect(command:String, ...rest):void{
				_originalUrl = command;
				super.connect(command,rest);
			}
			public function AppendToGatewayUrl(append:String):void{
				//experimental code
				//Alert.show("appendToGatewayUrl"+ this._originalUrl + "+" + append);
				connect(this._originalUrl + append);
			}
			public function AddHeader():void{
				//not implements.
			}
			
			public function ReplaceGatewayUrl():void{
				//not implements.	
			}
		}
}

