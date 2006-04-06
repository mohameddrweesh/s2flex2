package org.seasar.flex2.util.data.transfer;

import java.lang.reflect.Method;

import org.seasar.flex2.util.data.transfer.annotation.ConstantAnnotationHandler;
import org.seasar.flex2.util.data.transfer.annotation.Export;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;

public class TigerAnnotationHandler extends ConstantAnnotationHandler {
    public String getStorageType(BeanDesc beanDesc, PropertyDesc propertyDesc) {
        Method method = propertyDesc.getReadMethod();
        Export storageType = method.getAnnotation(Export.class);
        if (storageType != null) {
            return storageType.storage();
        }

        return null;
    }
}
