package samples.service.impl;

import org.seasar.flex2.rpc.remoting.service.annotation.RemotingService;
import org.seasar.framework.util.DomUtil;
import org.w3c.dom.Element;

import samples.dto.TestDto;
import samples.service.XmlService;
@RemotingService
public class XmlServiceImpl implements XmlService {

	public String doTest(TestDto testDto) {
		String contents = null;
		if(testDto!= null){
	        Element root = testDto.getXmlData().getDocumentElement();
	         contents = DomUtil.toString(root);
		}
		return contents;
	}

}
