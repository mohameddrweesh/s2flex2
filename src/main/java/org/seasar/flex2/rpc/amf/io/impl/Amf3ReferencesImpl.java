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

import org.seasar.flex2.rpc.amf.io.Amf3References;

public class Amf3ReferencesImpl implements Amf3References {

    protected Amf3ClassReferences classReferences;
    
    protected Amf3ObjectReferences objectReferences;

    protected Amf3ObjectReferences stringReferences;

    public Amf3ReferencesImpl() {
        objectReferences = new Amf3ObjectReferences();
        stringReferences = new Amf3ObjectReferences();
        classReferences = new Amf3ClassReferences();
    }

    public void addClassProperties(Class clazz, String[] properties) {
        classReferences.addProperties(clazz, properties);
    }

    public void addClassReference(Class clazz) {
        classReferences.addClassReference(clazz);
    }

    public void addObjectReference(Object object) {
        objectReferences.addReference(object);
    }

    public void addStringReference(String object) {
        stringReferences.addReference(object);
    }
    
    public void initialize() {
        objectReferences.initialize();
        stringReferences.initialize();
        classReferences.initialize();
    }

    public int getClassReferenceIndex(Class clazz) {
        return classReferences.getReferenceIndex(clazz);
    }

    public int getObjectReferenceIndex(Object object) {
        return objectReferences.getReferenceIndex(object);
    }
    
    public int getStringReferenceIndex(String object) {
        return stringReferences.getReferenceIndex(object);
    }
}