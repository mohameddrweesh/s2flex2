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
 
 * @igunore
 */
 
package org.seasar.flex2.rpc
{
	import flash.net.Responder;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	/**
	 * <h4>
	 * RelayResponder
	 * </h4>
	 */ 
	public class RelayResponder extends Responder
	{
		/** @private */
		private var _resultFunction:Function;
		/** @private */
		private var _statusFunction:Function;
		/** @private */
		private var _asyncToken:AsyncToken;
		/** @private */
		private var _remoteMessage:RemoteMessage;
		
		/**
		 * Constractor
		 * @param method call method name
		 * @param result resultFunction
		 * @param status statusFunciton
		 */
		public function RelayResponder(method:String,result:Function, status:Function=null)
		{
			super(onResultHandler, onFaultHandler);
			this._resultFunction = result;
			this._statusFunction=status;
			_remoteMessage = new RemoteMessage();
			_remoteMessage.operation=method;
			_remoteMessage.destination=method;
			
			_asyncToken = new AsyncToken(_remoteMessage);
		}
		/**
		 * 
		 * @param result
		 * 
		 */		
		private function onResultHandler(result:*):void
		{
			_remoteMessage.body="success";
	        _resultFunction( RemoteMessage( asyncToken.message ).operation, result );
			var resultEvent:ResultEvent=new ResultEvent("result",false,false,result,asyncToken,_remoteMessage);
	        if( _asyncToken.hasResponder() ) {
	            for( var i:int = 0; i < asyncToken.responders.length; i++ )
	                _asyncToken.responders[ i ].result( resultEvent );
	        }
		}
		
	    /**
	     *     @private
	     */
	    private function onFaultHandler( fault : * ):void
	    {
	        _remoteMessage.body = "fault";
        	_statusFunction( RemoteMessage( asyncToken.message ).operation, fault );
			var faultEvent:FaultEvent=new FaultEvent("fault",false,false,fault,asyncToken,_remoteMessage);
	        if( _asyncToken.hasResponder() ) {
	            for( var i:int = 0; i < asyncToken.responders.length; i++ )
	            {
	                _asyncToken.responders[ i ].fault( faultEvent );
	       		}
	    	}
		}
		/**
		 * このResponderのAsynTokenを返します
		 * @return AsyncToken このResponderに関連するAsyncToken
		 */ 
		public function get asyncToken():AsyncToken
		{
			return this._asyncToken;
		}
	}
}