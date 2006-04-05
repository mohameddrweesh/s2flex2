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
import java.util.List;

import org.w3c.dom.Document;

public class Amf3References {
    
    private List classReferences;

    private List objectReferences;

    private List propertyListReferences;

    private List stringReferences;

    protected Amf3References() {
        classReferences = new ArrayList();
        objectReferences = new ArrayList();
        stringReferences = new ArrayList();
        propertyListReferences = new ArrayList();
    }

    public void addClassReference(Class o) {
        classReferences.add(o);
    }

    public void addObjectReference(Object o) {
        objectReferences.add(o);
    }

    public void addProperties(String[] o) {
        propertyListReferences.add(o);
    }

    public void addStringReference(String o) {
        stringReferences.add(o);
    }

    public Class getClassAt(int index) {
        Object reference = classReferences.get(index);
        if (reference instanceof Class) {
            return (Class) reference;
        } else {
            return null;
        }
    }

    public int getClassReferenceIndex(Class o) {
        return classReferences.indexOf(o);
    }

    public Date getDateAt(int index) {
        Object reference = objectReferences.get(index);
        if (reference instanceof Date) {
            return (Date) reference;
        } else {
            return null;
        }
    }

    public List getListAt(int index) {
        Object reference = objectReferences.get(index);
        if (reference instanceof List) {
            return (List) reference;
        } else {
            return null;
        }
    }

    public Object getObjectAt(int index) {
        return objectReferences.get(index);
    }
    
    public int getObjectReferenceIndex(Object o) {
        return objectReferences.indexOf(o);
    }

    public String[] getPropertiesAt(int index) {
        Object reference = propertyListReferences.get(index);
        if (reference instanceof String[]) {
            return (String[]) reference;
        } else {
            return null;
        }
    }

    public String getStringAt(int index) {
        Object reference = stringReferences.get(index);
        if (reference instanceof String) {
            return (String) reference;
        } else {
            return null;
        }
    }

    public int getStringReferenceIndex(String o) {
        return stringReferences.indexOf(o);
    }

    public Document getXmlDocumentAt(int index) {
        Object reference = objectReferences.get(index);
        if (reference instanceof Document) {
            return (Document) reference;
        } else {
            return null;
        }
    }
}
