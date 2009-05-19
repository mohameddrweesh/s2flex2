/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf3.type.impl;

import java.util.ArrayList;
import java.util.Map;

import org.seasar.framework.util.ArrayMap;
import org.w3c.dom.Document;

public class Amf3ObjectReferences {

    private ArrayList referenceList;

    private Map referenceMap;

    public Amf3ObjectReferences() {
        referenceMap = new ArrayMap(256);
        referenceList = new ArrayList(256);
    }

    public void addReference(final Object value) {
        referenceList.add(value);
        referenceMap.put(value, Integer.valueOf(referenceList.size() - 1));
    }

    public void setReferenceAt(final int index, Object value) {
        Object currentValue = referenceList.get(index);
        if( currentValue != null ){
            referenceMap.remove(currentValue);
        }
        referenceList.add(index, value);
        referenceMap.put(value, Integer.valueOf(index));
    }
    
    public Object getAt(final int index) {
        return referenceList.get(index);
    }

    public int getReferenceIndex(final Object value) {
        final Integer index = (Integer) referenceMap.get(value);
        if (index != null) {
            return index.intValue();
        } else {
            return -1;
        }
    }

    public Document getXmlDocumentAt(final int index) {
        final Object reference = referenceList.get(index);
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
