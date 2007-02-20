package org.seasar.flex2.resources;

/**
 * @author higa
 *
 */
public class StringPiece implements MessagePiece {

	private String value;
	
	public StringPiece() {
	}

	/**
	 * @see org.seasar.flex.message.MessagePiece#getString(java.lang.Object[])
	 */
	public String getString(Object[] args) {
		return value;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
