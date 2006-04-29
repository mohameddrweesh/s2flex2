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
package org.seasar.flex2.util.transfer.annotation.handler;

import java.lang.reflect.Method;

import org.codehaus.backport175.reader.Annotation;
import org.codehaus.backport175.reader.Annotations;
import org.seasar.flex2.util.transfer.annotation.Export;
import org.seasar.flex2.util.transfer.annotation.Import;
import org.seasar.flex2.util.transfer.annotation.handler.impl.BasicAnnotationHandlerImpl;
import org.seasar.framework.beans.PropertyDesc;

public class Backport175ActionAnnotationHandler extends BasicAnnotationHandlerImpl {

	public String getExportStorageType(PropertyDesc propertyDesc) {
        Method method = propertyDesc.getReadMethod();
        Annotation annotation =Annotations.getAnnotation(Export.class,method);
        if(annotation!= null){
        	return ((Export)annotation).storage();
        }
        return null;
	}
    
	public String getImportStorageType(PropertyDesc propertyDesc) {
        Method method = propertyDesc.getWriteMethod();
        Annotation annotation =Annotations.getAnnotation(Import.class,method);
        if(annotation!= null){
        	return ((Import)annotation).storage();
        }
        return null;
	}
}

/*
Column column = (Column) getPropertyAnnotation(Column.class,pd);
return (column!=null)?column.value():null;

private Annotation getPropertyAnnotation(Class clazz,PropertyDesc pd){
	if(pd.getWriteMethod() != null){
		Annotation annotation = 
			Annotations.getAnnotation(clazz,pd.getWriteMethod());
		if(annotation!=null){
			return annotation;				
		}
	}
	if(pd.getReadMethod() != null){
		return Annotations.getAnnotation(clazz,pd.getReadMethod());
	}
	return null;
}
public String getColumnAnnotation(PropertyDesc pd) {
	Column column = (Column) getPropertyAnnotation(Column.class,pd);
	return (column!=null)?column.value():null;
}

*/
/*
Annotation annotation = 
Annotations.getAnnotation(clazz,pd.getWriteMethod());
if(annotation!=null){
return annotation;	
*/