package com.psbc.rm.dom.pbs.api;

import com.model.annotation.PBS;
import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import com.model.registry.registerhandler.IAtomicService;
import com.psbc.rm.dom.pbs.dto.PbsTest2_3InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_3OutputDTO;

@PBS(id = "Tt2_3", name = "PBS测试2_3", type = ServiceType.ORDINARY, oprAtt = OperateType.MAINTAIN)
public interface PbsTest2_3 extends IAtomicService<PbsTest2_3InputDTO, PbsTest2_3OutputDTO> {
}
