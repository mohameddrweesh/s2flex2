package org.seasar.flex2.rpc.responder
{
	import mx.rpc.IResponder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class DelegateResponder implements IResponder {
		
		protected var resultFunction:Function;
		
		protected var faultFunction:Function;
		
		public function DelegateResponder( resultFunction:Function, faultFunction:Function = null){
			this.resultFunction = resultFunction;
			this.faultFunction = faultFunction;
		}
		
		public function result(data:Object):void{
			resultFunction.call( null, data as ResultEvent );
		}
		
		public function fault(info:Object):void{
			if( faultFunction != null ){
				faultFunction.call( null, info as FaultEvent);
			}
		}
	}
}