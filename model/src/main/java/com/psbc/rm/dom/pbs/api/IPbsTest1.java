package com.psbc.rm.dom.pbs.api;


import com.model.annotation.PBS;
import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import com.model.registry.registerhandler.IAtomicService;
import com.psbc.rm.dom.pbs.dto.PbsTest1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest1OutputDTO;

@PBS(id = "Tt11", name = "PBS测试1", type = ServiceType.ORDINARY, oprAtt = OperateType.MAINTAIN)
public interface IPbsTest1 extends IAtomicService<PbsTest1InputDTO, PbsTest1OutputDTO> {
}
