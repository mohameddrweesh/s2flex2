// ActionScript file
import mx.utils.ObjectUtil;
import flash.events.Event;
import examples.flex2.dto.AddDto;
import mx.controls.Text;
import mx.rpc.events.ResultEvent;
import mx.rpc.events.FaultEvent;

public function calculate():void{
	var addDto:AddDto = new AddDto();
	addDto.arg1=int(arg1_txt.text);
	addDto.arg2=int(arg2_txt.text);
	amf.calculate2(addDto);
}
public function onResult(ret:ResultEvent):void{
	var retObj:AddDto = ret.result as AddDto;
	ans_txt.text=retObj.sum.toString();
}
public function onFault(ret:FaultEvent):void{
	ans_txt.text=ObjectUtil.toString(ret.fault);
}