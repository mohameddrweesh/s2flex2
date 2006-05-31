package org.seasar.flex2.rpc.remoting.service;

import org.seasar.flex2.rpc.remoting.service.dto.AddDto;

public interface AddService {

    public int calculate(int arg1, int arg2);

    public AddDto calculate1(AddDto addDto);

    public AddDto calculate2(AddDto addDto);
}
