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
package org.seasar.flex2.message.format.amf.io.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Amf3ClassReferences {

    private final List classReferences;

    private final Map propertiesReferences;

    public Amf3ClassReferences() {
        classReferences = new ArrayList(64);
        propertiesReferences = new HashMap(64);
    }

    public void addClassReference(Class o) {
        classReferences.add(o);
    }

    public void addProperties(Class clazz, String[] properties) {
        propertiesReferences.put(clazz, properties);
    }

    public Class getClassAt(int index) {
        Object reference = classReferences.get(index);
        if (reference != null) {
            return (Class) reference;
        } else {
            return null;
        }
    }

    public String[] getPropertiesAt(Class clazz ) {
        String[] reference = null;
        if( propertiesReferences.containsKey(clazz)){
            reference = (String[])propertiesReferences.get(clazz);
        }
        return reference;
    }

    public int getReferenceIndex(Class o) {
        return classReferences.indexOf(o);
    }

	public void initialize() {
		classReferences.clear();
		propertiesReferences.clear();
	}
}
