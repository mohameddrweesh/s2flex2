package examples.flex2.add.service.impl;

import examples.flex2.add.dto.AddDto;

public class AbstractService {

    /**
     * @Export(storage = "session")
     */
	protected AddDto addDto;
	
	
    /**
     * 
     * @Export(storage = "session") 
     */
	public AddDto getAddDto() {
		return addDto;
	}

    /**
     * 
     * @Import(storage = "session") 
     */
	public void setAddDto(AddDto addDto) {
		this.addDto = addDto;
	}
	
	
}
