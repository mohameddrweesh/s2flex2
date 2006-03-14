// ActionScript file
import flash.events.Event;

import mx.controls.Text;
import mx.rpc.Fault;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;
import mx.utils.ObjectUtil;

public function execute():void{
	amf.getExService("serviceName");
}
public function execute2():void{
	amf.getService("serviceName");
}
public function onResult(ret:ResultEvent):void{
}
public function onFault(ret:FaultEvent):void{
	var fault:Fault = ret.fault;
	faultcode_txt.text=fault.faultcode;
	description_txt.text=fault.description;
	message_txt.text=fault.message;
}