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
package org.seasar.flex2.message.format.amf.data;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.seasar.flex2.message.format.amf.data.AmfBody;
import org.seasar.flex2.message.format.amf.data.impl.AmfBodyImpl;
;

public class AmfBodyImplTest extends TestCase {

	private AmfBody body = new AmfBodyImpl("abc.def.Hoge.foo", "aaa", "111");

	public void testGetServiceName() {
		assertEquals("1", "abc.def.Hoge", body.getServiceName());
	}

	public void testGetServiceMethodName() {
		assertEquals("1", "foo", body.getServiceMethodName());
	}

	public void testGetArgs() throws Exception {
		Object[] args = body.getArgs();
		assertEquals("1", 1, args.length);
		assertEquals("2", "111", args[0]);
	}

	protected void setUp() throws Exception {
		ArrayList data = new ArrayList();
		data.add("111");
		body = new AmfBodyImpl("abc.def.Hoge.foo", "aaa", data);
	}
}