package com.psbc.rm.dom.pbs.impl;

import com.psbc.rm.dom.pbs.api.PbsTest2_2;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2OutputDTO;
import org.springframework.stereotype.Component;

@Component
public class PbsTest2_2Impl implements PbsTest2_2 {
    @Override
    public PbsTest2_2OutputDTO doService(PbsTest2_2InputDTO data) {
        PbsTest2_2OutputDTO outputDTO = new PbsTest2_2OutputDTO();
        outputDTO.setName("张四");
        return outputDTO;
    }

    @Override
    public PbsTest2_2OutputDTO compensate(PbsTest2_2InputDTO data) {
        System.out.println("222222222222222");
        return null;
    }
}
