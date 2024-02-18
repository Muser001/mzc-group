package com.psbc.rm.dom.pbs.api;

import com.model.annotation.PBS;
import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import com.model.registry.registerhandler.IAtomicService;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1OutputDTO;

@PBS(id = "Tt2_1", name = "PBS测试2_1", type = ServiceType.ORDINARY, oprAtt = OperateType.MAINTAIN)
public interface PbsTest2_1 extends IAtomicService<PbsTest2_1InputDTO, PbsTest2_1OutputDTO> {
}
