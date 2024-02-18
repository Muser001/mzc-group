package com.psbc.rm.dom.pcs.mapper;

import com.model.mapper.FlowOutputMapper;
import com.model.util.EntityDTOConvert;
import com.psbc.rm.dom.pcs.dto.T3122ExchangeDTO;
import com.psbc.rm.dom.pcs.dto.T3122InputDTO;
import com.psbc.rm.dom.pcs.dto.T3122OutputDTO;
import org.springframework.stereotype.Component;

@Component
public class T3122OutMapper implements FlowOutputMapper<T3122InputDTO, T3122ExchangeDTO, T3122OutputDTO> {
    @Override
    public void outputMapping(T3122InputDTO transInput, T3122ExchangeDTO exchange, T3122OutputDTO transOutput) {
        EntityDTOConvert.convert(exchange, transOutput);
    }
}
