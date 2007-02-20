package org.seasar.flex2.resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author higa
 *
 */
public class MessageManager implements Serializable {

	private Map messages = new HashMap();
	
	public MessageManager() {
	}
	
	public String getMessage(String messageCode, Object[] args) {
		Message message = (Message) messages.get(messageCode);
		if (message == null) {
			return "[" + messageCode + "]";
		}
		return message.getString(args);
	}

	/**
	 * @return Returns the messages.
	 */
	public Map getMessages() {
		return messages;
	}

	/**
	 * @param messages The messages to set.
	 */
	public void setMessages(Map messages) {
		this.messages = messages;
	}
	
	public void addMessage(String messageCode, Message message) {
		messages.put(messageCode, message);
	}
}
