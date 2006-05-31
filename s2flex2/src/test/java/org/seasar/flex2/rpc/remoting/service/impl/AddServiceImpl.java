package org.seasar.flex2.rpc.remoting.service.impl;

import org.seasar.flex2.rpc.remoting.service.AddService;
import org.seasar.flex2.rpc.remoting.service.dto.AddDto;

public class AddServiceImpl implements AddService {

    public AddServiceImpl() {
    }

    public AddDto calculate1(AddDto addDto) {
        addDto.setSum(addDto.getArg1() - addDto.getArg2());

        return addDto;
    }

    public int calculate(int arg1, int arg2) {
        return 0;
    }

    public AddDto calculate2(AddDto addDto) {
        return null;
    }
}
