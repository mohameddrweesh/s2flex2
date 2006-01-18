package org.seasar.flex2;

import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import flex.messaging.MessageException;
import flex.messaging.io.amf.ActionContext;
import flex.messaging.messages.Message;
import flex.messaging.messages.RemotingMessage;
import flex.messaging.services.remoting.adapters.JavaAdapter;

/**
 * @author higa
 * @author funakura 
 */
public class S2Adapter extends JavaAdapter {

	private ASTranslator translator = new ASTranslator();

	public S2Adapter() {
	}
	//@override	
    public Object invoke(Message message) {
    	RemotingMessage remotingMessage = (RemotingMessage) message;
    	String className = remotingMessage.getSource();
    	String methodName = remotingMessage.getOperation();
    	List parameters = remotingMessage.getParameters();
    	Object result=null;
    	S2Container container = SingletonS2ContainerFactory.getContainer();
		Object component = container.getComponent(className);
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
		try{
			result= beanDesc.invoke(component, methodName, convertArgList(parameters));
		}catch(Throwable t){
			MessageException me = new MessageException("invokeError",t);
			throw me;
		}
		return result;
    }
    
	protected Object[] convertArgList(List argList) throws Throwable {
		Object[] args = new Object[argList.size()];
		for (int i = 0; i < args.length; ++i) {
			args[i] = translator.fromActionScript(argList.get(i));
		}
		return args;
	}

	/**
	 * @see flashgateway.adapter.ServiceAdapter#supportsService(flashgateway.action.ActionContext,
	 *      java.lang.String, java.lang.String, java.util.List,
	 *      java.lang.String)
	 *      @deprecated 
	 */
	
	public boolean supportsService(ActionContext ctx, String name,
			String methodName, List argList, String arg4) throws Exception {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		return container.hasComponentDef(name);
	}
}