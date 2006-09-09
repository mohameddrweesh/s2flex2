package examples.flex2.add.service.impl;

import examples.flex2.add.dto.AddDto;
import examples.flex2.add.service.AddSessionService;
import examples.flex2.add.service.AddService;
import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;
import org.seasar.flex2.util.data.storage.StorageType;
import org.seasar.flex2.util.data.transfer.annotation.Export;
import org.seasar.flex2.util.data.transfer.annotation.Import;

/**
 * @ RemotingService
 * @ org.seasar.flex2.rpc.remoting.service.annotation.RemotingService
 */
@RemotingService
public class AddSessionServiceImpl implements AddSessionService {

	AddService addService;
	

    public int calculate(int arg1, int arg2) {
        return addService.calculate( arg1, arg2 );
    }

    public AddDto calculate2(AddDto addDto) {

        return addService.calculate2(addDto);
    }

    /**
     * @ Export(storage="session")
     */
    @Export(storage = StorageType.SESSION)
    public AddDto getAddDtoData(){
    	return addService.getAddDtoData();
    }

    
    /**
     * @ Import (storage="session")
     * @param addService
     */
    @Import(storage = StorageType.SESSION)
	public void setAddService(AddService addService) {
		this.addService = addService;
	}
    
}
