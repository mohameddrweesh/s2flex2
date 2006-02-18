// ActionScript file
import mx.utils.ObjectUtil;
import flash.events.Event;

public function calculate():void{	
	amf.remoteCall("calculate",arg1_txt.text,arg2_txt.text);
}
public function onResult(ret:Object){
	ans_txt.text=ret.result.toString();
}
public function onFault(ret:Object){
	ans_txt.text=ObjectUtil.toString(ret);
}