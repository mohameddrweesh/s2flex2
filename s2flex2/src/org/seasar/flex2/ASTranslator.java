package org.seasar.flex2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ClassUtil;
import org.w3c.dom.Document;

import flex.messaging.io.amf.ASObject;

/**
 * @author higa
 *  
 */
public class ASTranslator {

	private static final String REMOTE_CLASS = "_remoteClass";
	private static final String HASLISTENERS = "_haslisteners";

	public ASTranslator() {
	}

	public Object fromActionScript(Object obj) {
		return fromActionScript(obj, new HashMap());
	}

	protected boolean needConvert(Object obj) {
		if (obj instanceof String || obj == null || obj instanceof Number
				|| obj instanceof Date || obj instanceof Boolean
				|| obj instanceof Document) {
			return false;
		} else {
			return true;
		}
	}

	protected Object fromActionScript(Object obj, Map shared) {
		if (needConvert(obj)) {
			if (obj instanceof List) {
				return fromList((List) obj, shared);
			} else if (obj instanceof ASObject) {
				if (shared.containsKey(obj)) {
					return shared.get(obj);
				} else {
					return fromASObject((ASObject) obj, shared);
				}
			} else if (needConvertMapToList(obj)) {
				return convertMapToList((Map) obj, shared);
			} else {
				return obj;
			}
		} else {
			return obj;
		}
	}

	protected List fromList(List list, Map shared) {
		List newList = new ArrayList();
		for (int i = 0; i < list.size(); ++i) {
			newList.add(fromActionScript(list.get(i), shared));
		}
		return newList;
	}

	protected Object fromASObject(ASObject obj, Map shared) {
		String type = obj.getType();
		if (type == null) {
			type = (String) obj.get(REMOTE_CLASS);
		}
		if (type == null) {
			return obj;
		}
		Class clazz = ClassUtil.forName(type);
		Object bean = ClassUtil.newInstance(clazz);
		shared.put(obj, bean);
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
		for (Iterator i = obj.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			if (beanDesc.hasPropertyDesc(key)) {
				Object value = fromActionScript(obj.get(key), shared);
				PropertyDesc pd = beanDesc.getPropertyDesc(key);
				if (pd.hasWriteMethod()) {
					pd.setValue(bean, value);
				}
			}
		}
		return bean;
	}
	
	protected boolean needConvertMapToList(Object value) {
		return value instanceof Map && ((Map) value).containsKey(HASLISTENERS);
	}
	
	protected List convertMapToList(Map map, Map shared) {
		map.remove(HASLISTENERS);
		List list = new ArrayList();
		for (int i = 0; i < map.size(); ++i) {
			Object value = map.get(String.valueOf(i));
			list.add(fromActionScript(value, shared));
		}
		return list;
	}
}