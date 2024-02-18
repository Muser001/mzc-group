package com.psbc.rm.dom.pbs.api;

import com.model.annotation.PBS;
import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import com.model.registry.registerhandler.IAtomicService;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2OutputDTO;

@PBS(id = "Tt2_2", name = "PBS测试2_2", type = ServiceType.OUTBOUND, oprAtt = OperateType.MAINTAIN)
public interface PbsTest2_2 extends IAtomicService<PbsTest2_2InputDTO, PbsTest2_2OutputDTO> {
}
