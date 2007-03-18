package samples.service;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;

import samples.dto.TestDto;
@RemotingService
public interface XmlService {
	public String doTest(TestDto testDto);
}
