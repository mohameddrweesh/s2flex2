package examples.flex2.add.service.impl;

import examples.flex2.add.dto.AddDto;
import examples.flex2.add.service.AddService;

public class AddServiceImpl implements AddService {

	private AddDto addDto;
	
    public int calculate(int arg1, int arg2) {
        return arg1 + arg2;
    }

    public AddDto calculate2(AddDto addDto) {

        addDto.setSum(addDto.getArg1() + addDto.getArg2());
        this.addDto=addDto;
        return addDto;
    }
    
    public AddDto getAddDtoData(){
    	if(this.addDto != null){
    		return this.addDto;
    	}
    	this.addDto = new AddDto();
    	return this.addDto;
    }
    /** 
     * @Export(storage = "session") 
     */
	public AddDto getAddDto() {
		return addDto;
	}

    /**
     * @Import(storage = "session") 
     */
	public void setAddDto(AddDto addDto) {
		this.addDto = addDto;
	}
}
