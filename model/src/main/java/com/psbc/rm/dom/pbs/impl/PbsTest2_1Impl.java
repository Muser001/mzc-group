package com.psbc.rm.dom.pbs.impl;

import com.psbc.rm.dom.pbs.api.PbsTest2_1;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1OutputDTO;
import org.springframework.stereotype.Component;

@Component
public class PbsTest2_1Impl implements PbsTest2_1 {
    @Override
    public PbsTest2_1OutputDTO doService(PbsTest2_1InputDTO data) {
        PbsTest2_1OutputDTO outputDTO = new PbsTest2_1OutputDTO();
        if ("张三".equals(data.getName())) {
            outputDTO.setName(data.getName());
            return outputDTO;
        }else {
            outputDTO.setName(data.getName() + "00");
            return outputDTO;
        }

    }

    @Override
    public PbsTest2_1OutputDTO compensate(PbsTest2_1InputDTO data) {
        return null;
    }
}
