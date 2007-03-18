package examples.flex2.param.service;

import examples.flex2.param.dto.TypeADto;
import examples.flex2.param.dto.TypeBDto;
import examples.flex2.param.dto.TypeCDto;
import java.util.List;

public interface ParamCheckService {
	public TypeCDto getTypeCDto(int index,TypeADto aDto,TypeBDto b);
	public TypeADto getTypeADto(List l);
	public TypeBDto getTypeBDto(String[] b);
}
