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
package org.seasar.flex2.util.data.transfer.annotation.handler;

import org.seasar.flex2.util.data.transfer.annotation.Export;
import org.seasar.flex2.util.data.transfer.annotation.Import;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;

public class TigerAnnotationHandler implements AnnotationHandler {

	public String getExportStorageType(final BeanDesc beanDesc,
			final PropertyDesc propertyDesc) {
		Export exportAnno = null;

		do {
			if (propertyDesc.hasReadMethod()) {
				exportAnno = propertyDesc.getReadMethod().getAnnotation(
						Export.class);
				break;
			}
			if (propertyDesc.isReadable()) {
				exportAnno = propertyDesc.getField().getAnnotation(
						Export.class);
				break;
			}
		} while (false);

		return (exportAnno != null) ? exportAnno.storage() : null;
	}

	public String getImportStorageType(final BeanDesc beanDesc,
			final PropertyDesc propertyDesc) {
		Import importAnno = null;

		do {
			if (propertyDesc.hasWriteMethod()) {
				importAnno = propertyDesc.getWriteMethod().getAnnotation(
						Import.class);
				break;
			}
			if (propertyDesc.isWritable()) {
				importAnno = propertyDesc.getField().getAnnotation(
						Import.class);
				break;
			}
		} while (false);

		return (importAnno != null) ? importAnno.storage() : null;
	}
}
