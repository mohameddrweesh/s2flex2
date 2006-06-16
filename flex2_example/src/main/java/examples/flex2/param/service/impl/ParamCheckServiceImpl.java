package examples.flex2.param.service.impl;

import java.util.Calendar;

import examples.flex2.param.dto.TypeADto;
import examples.flex2.param.dto.TypeBDto;
import examples.flex2.param.dto.TypeCDto;

public class ParamCheckServiceImpl {
	public TypeCDto getTypeCDto(int index,TypeADto aDto,TypeBDto b){
		TypeCDto typeCDto = new TypeCDto();
		aDto.setAge(33);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,1973);
		cal.set(Calendar.MONTH,3);
		cal.set(Calendar.DATE,1);
		cal.getTime();
		aDto.setBirthDay(cal.getTime());
		b.setType(450);
		typeCDto.setA(aDto);
		typeCDto.setB(b);
		return typeCDto;		
	}
}
