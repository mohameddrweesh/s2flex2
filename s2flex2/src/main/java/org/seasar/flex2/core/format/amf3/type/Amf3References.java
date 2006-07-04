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
package org.seasar.flex2.core.format.amf3.type;

public interface Amf3References {

    void addClassProperties(Class clazz, String[] properties);

    void addClassReference(Class clazz);

    void addObjectReference(Object object);

    void addStringReference(String object);

    Class getClassAt(int index);

    int getClassReferenceIndex(Class clazz);

    Object getObjectAt(int index);

    int getObjectReferenceIndex(Object object);

    String[] getPropertiesAt(Class clazz);

    String getStringAt(int index);

    int getStringReferenceIndex(String object);

    void initialize();
}