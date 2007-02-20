package org.seasar.flex2.resources;

import java.util.List;

/**
 * @author higa
 *
 */
public class Message {

	private List messagePieces;

	public Message() {
	}
	
	public String getString(Object[] args) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < messagePieces.size(); ++i) {
			MessagePiece piece = (MessagePiece) messagePieces.get(i);
			buf.append(piece.getString(args));
		}
		return buf.toString();
	}

	/**
	 * @return Returns the messagePieces.
	 */
	public List getMessagePieces() {
		return messagePieces;
	}
	
	/**
	 * @param messagePieces The messagePieces to set.
	 */
	public void setMessagePieces(List messagePieces) {
		this.messagePieces = messagePieces;
	}
}
