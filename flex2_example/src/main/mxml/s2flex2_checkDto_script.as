// ActionScript file
import mx.rpc.events.ResultEvent;
import mx.rpc.events.FaultEvent;
import mx.controls.Alert;
import examples.flex2.dto.CheckDto;
import mx.utils.ObjectUtil;

[Bindable]
public var checkDto:CheckDto;
[Bindable]
public var checkDtoList:Array;

public function getCheckDto():void{
	checkDtoService.getCheckDto();
}
public function getCheckList():void{
	checkDtoServiceList.getCheckDtoList();
}
private function initApp():void
{
	var foo:CheckDto = new CheckDto();
	checkDtoList = new Array();
}
public function onListResult(event:ResultEvent):void{
	this.checkDtoList=event.result as Array;
}
public function onResult(event:ResultEvent):void{
	this.checkDto=event.result as CheckDto;
	array_txt.text=ObjectUtil.toString(checkDto.stringArray);
	list_txt.text=ObjectUtil.toString(checkDto.list);
}
public function onFault(event:FaultEvent):void{
	Alert.show("error=" +event);
}