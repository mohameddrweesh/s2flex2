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