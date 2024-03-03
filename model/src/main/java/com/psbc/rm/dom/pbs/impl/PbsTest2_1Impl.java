package com.psbc.rm.dom.pbs.impl;

import com.psbc.rm.dom.domain.Student;
import com.psbc.rm.dom.pbs.api.PbsTest2_1;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1OutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PbsTest2_1Impl implements PbsTest2_1 {

    @Autowired
    private Student student;
    @Override
    public PbsTest2_1OutputDTO doService(PbsTest2_1InputDTO data) {

        student.fun5();
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
        System.out.println("111111111111111111111111111111111");
        return null;
    }
}
