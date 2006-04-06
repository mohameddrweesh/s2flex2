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
        Transfer.exportTo(storage, testClass);
        assertEquals("1", session.getAttribute("strData"), testClass.getStrData() );
    }

    private TestClass createTarget() {
        TestClass testClass = new TestClass();
        testClass.setStrData("moji");
        return testClass;
    }

    private HttpSession createMockHttpSession() {
        HttpSession session = getRequest().getSession();
        return session;
    }    
}