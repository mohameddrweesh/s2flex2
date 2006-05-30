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

public function getDto():void{
	amf2.getAddDtoData();
}

public function onResultData(ret:ResultEvent):void{
	var retObj:AddDto = ret.result as AddDto;
	ret1_txt.text=retObj.arg1.toString();
	ret2_txt.text=retObj.arg2.toString();
	ret_sum_txt.text=retObj.sum.toString();
}
public function onFaultData(ret:FaultEvent):void{
	ans_txt.text=ObjectUtil.toString(ret.fault);
}