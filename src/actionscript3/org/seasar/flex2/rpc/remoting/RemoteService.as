package org.seasar.flex2.rpc.remoting
{
	public dynamic class RemoteService extends S2FlexService
	{
		[Inspectable(type="Boolean",defaultValue="false")]
   		public var useAMF0:Boolean;
   		
   		 protected override function initConnection():void{
			_con = new NetConnection();
			configureListeners(_con);
			if(useAMF0){
				_con.objectEncoding = ObjectEncoding.AMF0;
			}else{//default is AMF3
				_con.objectEncoding = ObjectEncoding.AMF3;
			}
			_con.addHeader("DescribeService", false, 0);
			var config:XML = ServerConfig.xml;
			if(this.gatewayUrl == null){	
				this.gatewayUrl=config.channels.channel.(@id==config..destination.(@id==this.destination).channels.channel.@ref).endpoint.@uri.toString();
			}
			_con.connect(this.gatewayUrl);	
		}
	}
}