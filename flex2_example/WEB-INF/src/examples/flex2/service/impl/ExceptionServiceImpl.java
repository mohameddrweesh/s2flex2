package examples.flex2.service.impl;

import examples.flex2.service.ExceptionService;

public class ExceptionServiceImpl implements ExceptionService {

	public void getExService(String serviceName) {
		if(true){
			throw new RuntimeException(serviceName + " is null",new NullPointerException("causeException"));
		}
	}

}
