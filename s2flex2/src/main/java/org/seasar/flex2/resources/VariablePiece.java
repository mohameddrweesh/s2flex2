package org.seasar.flex2.resources;

/**
 * @author higa
 *
 */
public class VariablePiece implements MessagePiece {

	private int index;
	
	public VariablePiece() {
	}

	/**
	 * @see org.seasar.flex.message.MessagePiece#getString(java.lang.Object[])
	 */
	public String getString(Object[] args) {
		Object arg = args[index];
		if (arg == null) {
			return "";
		}
		return arg.toString();
	}

	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
}
