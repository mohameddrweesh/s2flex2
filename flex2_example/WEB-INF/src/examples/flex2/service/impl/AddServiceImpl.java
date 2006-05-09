package examples.flex2.service.impl;

import examples.flex2.dto.AddDto;
import examples.flex2.service.AddService;

public class AddServiceImpl implements AddService {

    public int calculate(int arg1, int arg2) {
        return arg1 + arg2;
    }

    public AddDto calculate2(AddDto addDto) {
        addDto.setSum(addDto.getArg1() + addDto.getArg2());
        return addDto;
    }
}
