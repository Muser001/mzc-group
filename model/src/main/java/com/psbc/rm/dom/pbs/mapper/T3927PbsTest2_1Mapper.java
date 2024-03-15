package com.psbc.rm.dom.pbs.mapper;

import com.model.mapper.ServiceNodeMapper;
import com.model.util.EntityDTOConvert;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_1OutputDTO;
import com.psbc.rm.dom.pcs.dto.T3927ExchangeDTO;
import com.psbc.rm.dom.pcs.dto.T3927InputDTO;
import org.springframework.stereotype.Component;

@Component("t3927PbsTest2_1Mapper")
public class T3927PbsTest2_1Mapper implements
        ServiceNodeMapper<T3927InputDTO, T3927ExchangeDTO, PbsTest2_1InputDTO, PbsTest2_1OutputDTO> {
    @Override
    public PbsTest2_1InputDTO inputMapping(T3927InputDTO transInput, T3927ExchangeDTO exchange) {
        PbsTest2_1InputDTO inputDTO = new PbsTest2_1InputDTO();
        EntityDTOConvert.convert(transInput, inputDTO);
        return inputDTO;
    }

    @Override
    public void outputMapping(PbsTest2_1OutputDTO atomicOutput, T3927ExchangeDTO exchange) {
        EntityDTOConvert.convert(atomicOutput, exchange);
    }
}
