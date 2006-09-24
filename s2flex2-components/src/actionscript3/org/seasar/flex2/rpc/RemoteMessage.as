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
 * @ignore
 */
 package org.seasar.flex2.rpc
{
	import mx.messaging.messages.IMessage;
	import mx.messaging.messages.AbstractMessage;

	public class RemoteMessage extends AbstractMessage implements IMessage
	{
		private var _operation:String;
		
		public function get operation():String
		{
			return this._operation;
		}
		public function set operation(value:String):void
		{
			this._operation=value;
		}
		/*
		public override function get clientId():String
		{
			return super.clientId;
			
		}
		
		public override function set clientId(value:String):void
		{
			super.clientId=value;
		}
		
		public override function toString():String
		{
			return "RemoteMessage";
		}
		
		public override function get headers():Object
		{
			return super.headers;
		}
		
		public override function set headers(value:Object):void
		{
			super.headers=value;
		}
		
		public override function get body():Object
		{
			return super.body;
		}
		
		public override function set body(value:Object):void
		{
			super.body=value;
		}
		
		public override function get timestamp():Number
		{
			return super.timestamp;
		}
		
		public  override function set timestamp(value:Number):void
		{
			super.timestamp;
		}
		
		public override function get messageId():String
		{
			return super.messageId;
		}
		
		public override function set messageId(value:String):void
		{
			super.messageId=value;
		}
		
		public override function get destination():String
		{
			return super.destination;
		}
		
		public override function set destination(value:String):void
		{
			super.destination= value;
		}
		
		public override function get timeToLive():Number
		{
			return super.timeToLive;
		}
		
		public override function set timeToLive(value:Number):void
		{
			super.timeToLive;
		}
		*/
		
	}
}