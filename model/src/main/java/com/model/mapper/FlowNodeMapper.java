package com.model.mapper;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.dto.FlowExchangeDTO;

/**
 * 不同节点，bean，method数据映射接口
 */
public interface FlowNodeMapper<TI extends BaseInputDTO, EX extends FlowExchangeDTO, NI extends BaseOutputDTO,
        NO extends BaseOutputDTO> {

    NI inputMapping(TI transInput, EX exchange);
    void outputMapping(NO output, EX exchange);
}
