package test.org.seasar.flex2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.flex2.ASTranslator;

import flex.messaging.io.amf.ASObject;;

public class ASTranslatorTest extends TestCase {

	private ASTranslator translator_ = new ASTranslator();

	public ASTranslatorTest(String arg0) {
		super(arg0);
	}

	public void testFromActionScript() {
		ASObject obj = new ASObject(MyBean.class.getName());
		obj.put("aaa", "hoge");
		List bbb = new ArrayList();
		ASObject obj2 = new ASObject(MyBean.class.getName());
		obj2.put("aaa", "hoge2");
		List bbb2 = new ArrayList();
		bbb2.add(obj);
		obj2.put("bbb", bbb2);
		bbb.add(obj2);
		obj.put("bbb", bbb);
		
		MyBean mybean = (MyBean) translator_.fromActionScript(obj);
		assertNotNull("1", mybean);
		assertEquals("2", "hoge", mybean.getAaa());
		assertEquals("3", 1, mybean.getBbb().size());
		MyBean mybean2 = (MyBean) mybean.getBbb().get(0);
		assertEquals("4", "hoge2", mybean2.getAaa());
		assertEquals("5", 1, mybean2.getBbb().size());
		assertSame("6", mybean, mybean2.getBbb().get(0));
	}
	
	public void testFromActionScript2() {
		Map map = new HashMap();
		map.put("0", "111");
		map.put("1", "222");
		map.put("2", "333");
		map.put("_haslisteners", Boolean.TRUE);
		List list = (List) translator_.fromActionScript(map);
		assertNotNull("1", list);
		assertEquals("2", 3, list.size());
		assertEquals("3", "111", list.get(0));
		assertEquals("4", "222", list.get(1));
		assertEquals("5", "333", list.get(2));
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ASTranslatorTest.class);
	}

	protected void setUp() throws Exception {
	}

	protected void tearDown() throws Exception {
	}

	public static class MyBean {
		private String aaa;
		private List bbb;
		/**
		 * @return Returns the aaa.
		 */
		public String getAaa() {
			return aaa;
		}
		/**
		 * @param aaa The aaa to set.
		 */
		public void setAaa(String aaa) {
			this.aaa = aaa;
		}
		/**
		 * @return Returns the bbb.
		 */
		public List getBbb() {
			return bbb;
		}
		/**
		 * @param bbb The bbb to set.
		 */
		public void setBbb(List bbb) {
			this.bbb = bbb;
		}
	}
}