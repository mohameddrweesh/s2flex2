package examples.flex2.param.service.impl;

import java.util.Calendar;
import java.util.List;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

import examples.flex2.param.dto.TypeADto;
import examples.flex2.param.dto.TypeBDto;
import examples.flex2.param.dto.TypeCDto;
import examples.flex2.param.service.ParamCheckService;

@RemotingService
public class ParamCheckServiceImpl implements ParamCheckService{
	public TypeCDto getTypeCDto(int index,TypeADto aDto,TypeBDto b){
		TypeCDto typeCDto = new TypeCDto();
		aDto.setAge(20);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,1983);
		cal.set(Calendar.MONTH,10);
		cal.set(Calendar.DATE,1);
		cal.getTime();
		aDto.setBirthDay(cal.getTime());
		b.setType(450);
		typeCDto.setA(aDto);
		typeCDto.setB(b);
		return typeCDto;		
	}

	public TypeADto getTypeADto(List l) {
		TypeADto typeAdto = new TypeADto();
		typeAdto.setAge(l.size());
		typeAdto.setLastName("lastName");
		typeAdto.setFirstName("firstName");
		return typeAdto;
	}

	public TypeBDto getTypeBDto(String[] b) {
		TypeBDto typeBDto = new TypeBDto();
		typeBDto.setDept(b[0]);
		typeBDto.setDeptCd(b[1]);
		
		return typeBDto;
	}


	
}
