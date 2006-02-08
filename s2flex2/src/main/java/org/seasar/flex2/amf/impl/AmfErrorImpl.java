/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.flex2.amf.impl;

import org.seasar.flex2.amf.AmfError;

public class AmfErrorImpl implements AmfError {

	private String code = "SERVER.PROCESSING";
	private String type;
	private String level = "error";
	private String details;
	private String description;
	
	public AmfErrorImpl(Throwable t) {
		type = t.getClass().getName();
		details = getStackTraceString(t);
		description = t.getMessage();
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getLevel() {
		return level;
	}

	public String getDetails() {
		return details;
	}

	public String getDescription() {
		return description;
	}
	
	private static String getStackTraceString(Throwable t) {
		StackTraceElement[] elements = t.getStackTrace();
		StringBuffer buf = new StringBuffer(t.toString());
		buf.append('\n');
		for (int i = 0; i < elements.length; ++i) {
			buf.append(elements[i].toString());
			buf.append('\n');
		}
		return buf.toString();
	}
}