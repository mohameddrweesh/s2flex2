/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.flex2.core.format.amf3.io.reader.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

import org.seasar.flex2.core.format.amf3.io.reader.Amf3DataReader;
import org.seasar.framework.util.ArrayUtil;
import org.seasar.framework.util.ClassUtil;

public class Amf3VectorReaderImpl extends AbstractAmf3TypedObjectReaderImpl
		implements Amf3DataReader {

	public Object read(final DataInputStream inputStream) throws IOException {
		return readObject(inputStream);
	}

	private final Object readVectorData(final int vectorDef,
			final DataInputStream inputStream) throws IOException {
		final int arrayLength = vectorDef >> 1;

		Object result;

		//区切り
		inputStream.readByte();

		final String className = (String) amf3StringReader.read(inputStream);
		if (className.length() > 0) {
			result = readTypedObjectVectorData(arrayLength, inputStream,
					className);
		} else {
			result = readBuildInClassVector(arrayLength, inputStream);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private Object readBuildInClassVector(final int arrayLength,
			final DataInputStream inputStream) throws IOException {
		Object tempArray = Array.newInstance(Object.class, arrayLength);
		addObjectReference(tempArray);
        Object result = tempArray;

		if (arrayLength > 0) {
			for (int i = 0; i < arrayLength; i++) {
				Array.set(tempArray, i, readPropertyValue(inputStream));
			}
			Class classType = Array.get(tempArray, 0).getClass();
			
			result = Array.newInstance(classType, arrayLength);
			for (int i = 0; i < arrayLength; i++) {
                Array.set(result, i, Array.get(tempArray, i));
            }
		}

		return result;
	}

	@SuppressWarnings("unchecked") 
	private Object readTypedObjectVectorData(final int arrayLength,
			final DataInputStream inputStream, final String className)
			throws IOException {

		final Class clazz = ClassUtil.forName(className);
		final Object result = Array.newInstance(clazz, arrayLength);
		addObjectReference(result);

		for (int i = 0; i < arrayLength; i++) {
			Array.set(result, i, readPropertyValue(inputStream));
		}
		return result;
	}

	protected final Object readInlinedObject(final int reference,
			final DataInputStream inputStream) throws IOException {
		return readVectorData(reference, inputStream);
	}

	protected final Object readReferencedObject(final int reference,
			final DataInputStream inputStream) {
		return getObjectAt(reference >>> 1);
	}
}