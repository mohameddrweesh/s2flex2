package org.seasar.flex2.resources;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.seasar.framework.util.LocaleUtil;
import org.seasar.framework.util.ResourceBundleUtil;

/**
 * @author higa
 *
 */
public class MessageLogicImpl implements MessageLogic {

	public MessageLogicImpl() {
	}

	/**
	 * @see org.seasar.flex.message.MessageLogic#getMessageManager(java.lang.String, java.lang.String)
	 */
	public MessageManager getMessageManager(String path, String localeStr) {
		Locale locale = LocaleUtil.getLocale(localeStr);
		ResourceBundle bundle = ResourceBundleUtil.getBundle(path, locale);
		return getMessageManager(bundle);
	}
	
	protected MessageManager getMessageManager(ResourceBundle bundle) {
		MessageManager manager = new MessageManager();
		for (Enumeration e = bundle.getKeys(); e.hasMoreElements(); ) {
			String messageCode = (String) e.nextElement();
			String pattern = bundle.getString(messageCode);
			Message message = getMessage(pattern);
			manager.addMessage(messageCode, message);
		}
		return manager;
	}
	
	protected Message getMessage(String pattern) {
		Message message = new Message();
		message.setMessagePieces(getMessagePieces(pattern));
		return message;
	}

	protected List getMessagePieces(String pattern) {
		List pieces = new ArrayList();
		PatternTokenizer tokenizer = new PatternTokenizer(pattern);
		for (int tokenType = tokenizer.next(); tokenType != PatternTokenizer.EOF; tokenType = tokenizer.next()) {
			switch (tokenType) {
			case PatternTokenizer.STRING:
				StringPiece strPiece = new StringPiece();
				strPiece.setValue(tokenizer.getToken());
				pieces.add(strPiece);
				break;
			case PatternTokenizer.VARIABLE:
				VariablePiece varPiece = new VariablePiece();
				varPiece.setIndex(tokenizer.getVarIndex());
				pieces.add(varPiece);
				break;
			}
		}
		return pieces;
	}
}
