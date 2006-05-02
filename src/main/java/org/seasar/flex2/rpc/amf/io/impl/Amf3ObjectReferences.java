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
package org.seasar.flex2.rpc.amf.io.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

public class Amf3ObjectReferences {

    private Map referenceMap;
    
    private ArrayList referenceList;

    public Amf3ObjectReferences() {
    	referenceMap = new HashMap(256);
    	referenceList = new ArrayList(256);
    }

    public void addReference(Object value) {
    	referenceList.add(value);
    	referenceMap.put(value,new Integer( referenceList.size() -1 ));
    }

    public Date getDateAt(int index) {
        Object reference = referenceList.get(index);
        if (reference instanceof Date) {
            return (Date) reference;
        } else {
            return null;
        }
    }

    public List getListAt(int index) {
        Object reference = referenceList.get(index);
        if (reference instanceof List) {
            return (List) reference;
        } else {
            return null;
        }
    }

    public Object getAt(int index) {
        return referenceList.get(index);
    }
    
    public int getReferenceIndex(Object value) {
    	Integer index = (Integer)referenceMap.get(value);
    	if( index != null ){
    		return index.intValue();
    	} else {
    		return -1;
    	}
    }

    public Document getXmlDocumentAt(int index) {
        Object reference = referenceList.get(index);
        if (reference instanceof Document) {
            return (Document) reference;
        } else {
            return null;
        }
    }

	public void initialize() {
    	referenceMap.clear();
    	referenceList.clear();
	}
}
