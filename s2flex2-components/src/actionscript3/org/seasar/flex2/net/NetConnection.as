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
 *
 * @ignore
 */
package org.seasar.flex2.net
{
    
    import flash.net.NetConnection;

    public class NetConnection extends flash.net.NetConnection{
        
        private var _originalUrl:String;
        
        private var append:String =null;
        
        public override function connect(command:String, ...rest):void
        {
            _originalUrl = command;
            super.connect(command,rest);
        }
        
        private function reconnect( uri:String ):void
        {
			if( this.connected ){
				this.close();
			}
			this.connect( uri );
		}
		
        public function AppendToGatewayUrl(append:String):void
        { 
            this.append = append;
            var url:String=this._originalUrl+ append;
            
            reconnect(url);
        }
        
        public override function addHeader(operation:String,mustUnderstand:Boolean=false,param:Object=null):void
        {
            super.addHeader(operation,mustUnderstand,param);
        }
        
        public function ReplaceGatewayUrl(url:String):void
        {
            reconnect(url);
        }
        
        public function get connectedUrl():String
        {
            if(append!= null){
                return _originalUrl + append;
            }
            return _originalUrl;
        }
    }
}