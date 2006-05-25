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
package org.seasar.flex2.rpc.amf.io.writer.data.impl.amf3;

import java.io.DataOutputStream;
import java.io.IOException;

import org.seasar.flex2.rpc.amf.data.Amf3DataType;
import org.seasar.flex2.rpc.amf.io.Amf3References;
import org.seasar.flex2.rpc.amf.io.factory.Amf3ReferencesFactory;

public abstract class AbstractAmf3ObjectWriterImpl extends
		AbstractAmf3UTF8StringWriterImpl {

	protected Amf3ReferencesFactory referencesFactory;

	protected final void addClassProperties( final Class clazz, final String[] properties) {
		Amf3References references = getReferences();
		references.addClassProperties(clazz, properties);
	}

	protected final void addClassReference( final Class clazz) {
		Amf3References references = getReferences();
		references.addClassReference(clazz);
	}

	protected final void addObjectReference( final Object object) {
		Amf3References references = getReferences();
		references.addObjectReference(object);
	}

	protected final void addStringReference( final String object) {
		Amf3References references = getReferences();
		references.addStringReference(object);
	}

	protected final int getClassReferenceIndex( final Class clazz) {
		Amf3References references = getReferences();
		return references.getClassReferenceIndex(
				clazz);
	}

	protected final int getObjectReferenceIndex( final Object object) {
		Amf3References references = getReferences();
		return references.getObjectReferenceIndex(
				object);
	}

	public abstract int getObjectType();

	private final Amf3References getReferences() {
		Amf3References references = referencesFactory.createReferences();
		return references;
	}

	protected final int getStringReferenceIndex(String object) {
		Amf3References references = getReferences();
		return references.getStringReferenceIndex(
				object);
	}

	protected abstract void processWriteObjectData(final Object object,
			final DataOutputStream outputStream) throws IOException;

	public final void setReferencesFactory(
			Amf3ReferencesFactory referencesFactory) {
		this.referencesFactory = referencesFactory;
	}

	public final void write(final Object value,
			final DataOutputStream outputStream) throws IOException {
		writeAMF3DataMaker(outputStream);
		writeObjectData(value, outputStream);
	}

	private final void writeAMF3DataMaker( final DataOutputStream outputStream)
			throws IOException {
		outputStream.writeByte(Amf3DataType.AMF3_DATA_MARKER);
	}

	public final void writeData( final Object value, final DataOutputStream outputStream)
			throws IOException {
		writeObjectData(value, outputStream);
	}

	protected final void writeObjectData(final Object object,
			final DataOutputStream outputStream) throws IOException {
		outputStream.writeByte(getObjectType());
		int referenceIndex = getObjectReferenceIndex(object);
		if (referenceIndex >= 0) {
			writeReferenceIndex(referenceIndex, outputStream);
		} else {
			processWriteObjectData(object, outputStream);
		}
	}

	protected final void writeReferenceIndex( final int index,
			 final DataOutputStream outputStream) throws IOException {
		writeIntData(index << 1, outputStream);
	}
}