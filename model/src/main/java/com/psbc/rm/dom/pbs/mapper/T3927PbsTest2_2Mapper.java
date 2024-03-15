package com.psbc.rm.dom.pbs.mapper;

import com.model.mapper.ServiceNodeMapper;
import com.model.util.EntityDTOConvert;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2InputDTO;
import com.psbc.rm.dom.pbs.dto.PbsTest2_2OutputDTO;
import com.psbc.rm.dom.pcs.dto.T3927ExchangeDTO;
import com.psbc.rm.dom.pcs.dto.T3927InputDTO;
import org.springframework.stereotype.Component;

@Component("t3927PbsTest2_2Mapper")
public class T3927PbsTest2_2Mapper implements
        ServiceNodeMapper<T3927InputDTO, T3927ExchangeDTO, PbsTest2_2InputDTO, PbsTest2_2OutputDTO> {
    @Override
    public PbsTest2_2InputDTO inputMapping(T3927InputDTO transInput, T3927ExchangeDTO exchange) {
        PbsTest2_2InputDTO inputDTO = new PbsTest2_2InputDTO();
        EntityDTOConvert.convert(transInput, inputDTO);
        EntityDTOConvert.convert(exchange, inputDTO);
        return inputDTO;
    }

    @Override
    public void outputMapping(PbsTest2_2OutputDTO atomicOutput, T3927ExchangeDTO exchange) {
        EntityDTOConvert.convert(atomicOutput, exchange);
    }
}
