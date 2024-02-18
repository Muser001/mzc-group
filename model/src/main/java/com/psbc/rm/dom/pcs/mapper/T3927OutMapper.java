package com.psbc.rm.dom.pcs.mapper;

import com.model.mapper.FlowOutputMapper;
import com.model.util.EntityDTOConvert;
import com.psbc.rm.dom.pcs.dto.*;
import org.springframework.stereotype.Component;

@Component
public class T3927OutMapper implements FlowOutputMapper<T3927InputDTO, T3927ExchangeDTO, T3927OutputDTO> {
    @Override
    public void outputMapping(T3927InputDTO transInput, T3927ExchangeDTO exchange, T3927OutputDTO transOutput) {
        EntityDTOConvert.convert(exchange, transOutput);
    }
}
