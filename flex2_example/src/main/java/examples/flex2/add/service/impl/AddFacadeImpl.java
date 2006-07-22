package examples.flex2.add.service.impl;

import examples.flex2.add.dto.AddDto;
import examples.flex2.add.service.AddFacade;
import examples.flex2.add.service.AddService;

public class AddFacadeImpl implements AddFacade {

	AddService addService;
	

    public int calculate(int arg1, int arg2) {
        return addService.calculate( arg1, arg2 );
    }

    public AddDto calculate2(AddDto addDto) {

        return addService.calculate2(addDto);
    }
    
    public AddDto getAddDtoData(){
    	return addService.getAddDtoData();
    }

    
    
	public void setAddService(AddService addService) {
		this.addService = addService;
	}
    
}
