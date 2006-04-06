package org.seasar.flex2.util.data.transfer;

import java.util.Enumeration;

public interface Storage {
    
    String getName();
    
    Object getProperty(String name);

    Enumeration getPropertyNames();

    void setProperty(String name, Object value);
}
