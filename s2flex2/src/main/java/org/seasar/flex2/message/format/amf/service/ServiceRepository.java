package org.seasar.flex2.message.format.amf.service;

public interface ServiceRepository {
    void addService(String serviceName, Object service);

    void clearService();

    Object getService(final String serviceName);
    
    boolean hasService(String serviceName);
    
    void removeService(String serviceName);
}