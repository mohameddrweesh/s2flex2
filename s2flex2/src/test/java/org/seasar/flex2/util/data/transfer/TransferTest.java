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
package org.seasar.flex2.util.data.transfer;

import javax.servlet.http.HttpSession;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.flex2.util.data.transfer.Storage;
import org.seasar.flex2.util.data.transfer.Transfer;
import org.seasar.flex2.util.data.transfer.impl.HttpSessionDataStorage;

public class TransferTest extends S2TestCase {

    protected void setUp() throws Exception {
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public TransferTest(String name) {
        super(name);
    }

    public void testImport() {
        HttpSession session = createMockHttpSession();
        Storage storage = new HttpSessionDataStorage( session );
        TestClass testClass = createTarget();
        Transfer.importTo(storage,testClass);
        assertEquals("1", testClass.getStrData(), session.getAttribute("strData"));
        assertEquals("2", testClass.getIntData(), session.getAttribute("intData"));
        assertEquals("3", testClass.getIntArrayData(), session.getAttribute("intArrayData"));
    }

    private TestClass createTarget() {
        return new TestClass();
    }

    private HttpSession createMockHttpSession() {
        HttpSession session = getRequest().getSession();
        session.setAttribute("strData","moji");
        session.setAttribute("intData",new Integer(10));
        session.setAttribute("intArrayData",new int[]{1,2});
        return session;
    }    
}