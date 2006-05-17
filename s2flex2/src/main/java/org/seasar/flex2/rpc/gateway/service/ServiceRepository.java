package org.seasar.flex2.rpc.gateway.service;

public interface ServiceRepository {
    Object getService(final String serviceName);

    void addService(String serviceName, Object service);

    boolean hasService(String serviceName);
}