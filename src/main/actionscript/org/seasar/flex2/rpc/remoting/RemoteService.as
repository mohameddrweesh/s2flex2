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

package org.seasar.flex2.rpc.remoting
{
	import flash.net.ObjectEncoding;
	
	public dynamic class RemoteService extends S2Flex2Service {
	    
	    [Inspectable(type="Boolean",defaultValue="false")]
   		public var useAMF0:Boolean;
   		
   		override protected  function createConnection():void{
            super.createConnection();
			if(useAMF0){
				_con.objectEncoding = ObjectEncoding.AMF0;
			}else{//default is AMF3
				_con.objectEncoding = ObjectEncoding.AMF3;
			}
        }
	}
}