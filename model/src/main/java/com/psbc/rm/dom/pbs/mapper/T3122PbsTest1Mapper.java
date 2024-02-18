package com.psbc.rm.dom.pbs.mapper;

import com.model.mapper.ServiceNodeMapper;
import com.model.util.EntityDTOConvert;
import com.psbc.rm.dom.pbs.dto.PbsTest1InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest1OutputDTO;
import com.psbc.rm.dom.pcs.dto.T3122ExchangeDTO;
import com.psbc.rm.dom.pcs.dto.T3122InputDTO;
import org.springframework.stereotype.Component;

@Component("t3122PbsTest1Mapper")
public class T3122PbsTest1Mapper implements
        ServiceNodeMapper<T3122InputDTO, T3122ExchangeDTO, PbsTest1InputDTO, PbsTest1OutputDTO> {
    @Override
    public PbsTest1InputDTO inputMapping(T3122InputDTO transInput, T3122ExchangeDTO exchange) {
        PbsTest1InputDTO inputDTO = new PbsTest1InputDTO();
        EntityDTOConvert.convert(transInput, inputDTO);
        return inputDTO;
    }

    @Override
    public void outputMapping(PbsTest1OutputDTO atomicOutput, T3122ExchangeDTO exchange) {
        EntityDTOConvert.convert(atomicOutput, exchange);
    }
}
