package org.seasar.flex2.resources;

/**
 * @author higa
 *  
 */
public class PatternTokenizer {
	
	public static final int STRING = 0;
	public static final int VARIABLE = 1;
	public static final int EOF = 9;

	private String pattern;

	private int position = 0;

	private String token;
	
	private int varIndex = -1;

	private int tokenType = STRING;

	private int nextTokenType = STRING;

	public PatternTokenizer(String pattern) {
		this.pattern = pattern;
	}

	public int getPosition() {
		return position;
	}

	public String getToken() {
		return token;
	}
	
	public int getVarIndex() {
		return varIndex;
	}

	public int getTokenType() {
		return tokenType;
	}

	public int getNextTokenType() {
		return nextTokenType;
	}

	public int next() {
		if (position >= pattern.length()) {
			token = null;
			tokenType = EOF;
			nextTokenType = EOF;
			return tokenType;
		}
		switch (nextTokenType) {
		case STRING:
			parseString();
			break;
		case VARIABLE:
			parseVariable();
			break;
		default:
			parseEof();
			break;
		}
		return tokenType;
	}

	protected void parseString() {
		int varStartPos = pattern.indexOf("{", position);
		if (varStartPos < 0) {
			token = pattern.substring(position);
			nextTokenType = EOF;
			position = pattern.length();
			tokenType = STRING;
		} else {
			token = pattern.substring(position, varStartPos);
			tokenType = STRING;
			boolean needNext = varStartPos == position;
			nextTokenType = VARIABLE;
			position = varStartPos + 1;
			if (needNext) {
				next();
			}
		}
	}

	protected void parseVariable() {
		int varEndPos = pattern.indexOf("}", position);
		if (varEndPos < 0) {
			throw new IllegalArgumentException(pattern);
		}
		token = null;
		varIndex = Integer.parseInt(pattern.substring(position, varEndPos));
		nextTokenType = STRING;
		position = varEndPos + 1;
		tokenType = VARIABLE;
	}
	
	protected void parseEof() {
		token = null;
		tokenType = EOF;
		nextTokenType = EOF;
	}
}