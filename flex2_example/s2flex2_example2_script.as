// ActionScript file
import mx.utils.ObjectUtil;
import flash.events.Event;
import examples.flex2.dto.AddDto;
import mx.controls.Text;

public function calculate():void{
	var addDto:AddDto = new AddDto();
	addDto.arg1=int(arg1_txt.text);
	addDto.arg2=int(arg2_txt.text);
	amf.remoteCall("calculate2",addDto);
}
public function onResult(ret:Object){
	ans_txt.text=ret.result.sum.toString();
}
public function onFault(ret:Object){
	ans_txt.text=ObjectUtil.toString(ret);
}