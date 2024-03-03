package com.psbc.rm.dom.pbs.impl;

import com.psbc.rm.dom.domain.Student;
import com.psbc.rm.dom.pbs.api.PbsTest2_3;
import com.psbc.rm.dom.pbs.dto.PbsTest2_3InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_3OutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PbsTest2_3Impl implements PbsTest2_3 {

    @Autowired
    private Student student;
    @Override
    public PbsTest2_3OutputDTO doService(PbsTest2_3InputDTO data) {

        student.fun6();
        int a = 1/0;
        PbsTest2_3OutputDTO outputDTO = new PbsTest2_3OutputDTO();
        outputDTO.setName(data.getName());
        return outputDTO;
    }

    @Override
    public PbsTest2_3OutputDTO compensate(PbsTest2_3InputDTO data) {
        System.out.println("3333333333333333333333333333333333333333333");
        return null;
    }
}
