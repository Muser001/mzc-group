package com.psbc.rm.dom.pbs.impl;

import com.psbc.rm.dom.pbs.api.IPbsTest1;
import com.psbc.rm.dom.pbs.dto.PbsTest1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest1OutputDTO;
import org.springframework.stereotype.Component;

@Component
public class PbsTest1Impl implements IPbsTest1 {
    @Override
    public PbsTest1OutputDTO doService(PbsTest1InputDTO data) {
        PbsTest1OutputDTO out = new PbsTest1OutputDTO();
        String name = data.getName();
        out.setName(name);
        return out;
    }

    @Override
    public PbsTest1OutputDTO compensate(PbsTest1InputDTO data) {
        return null;
    }
}
