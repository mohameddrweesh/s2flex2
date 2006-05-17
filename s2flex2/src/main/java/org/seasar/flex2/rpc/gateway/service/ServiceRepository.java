package org.seasar.flex2.rpc.gateway.service;

public interface ServiceRepository {
    void addService(String serviceName, Object service);

    Object getService(final String serviceName);

    boolean hasService(String serviceName);
}