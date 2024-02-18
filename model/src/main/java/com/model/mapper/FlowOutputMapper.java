package com.model.mapper;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.dto.FlowExchangeDTO;

/**
 * 流程映射接口--映射都要实现
 */
public interface FlowOutputMapper<TI extends BaseInputDTO, EX extends FlowExchangeDTO, TO extends BaseOutputDTO> {

    /**
     * 服务编排执行完，将输入和交换区数据映射到输出DTO
     * @param transInput 流程输入DTO
     * @param exchange 流程交换DTO
     * @param transOutput 流程输出DTO
     */
    void outputMapping(TI transInput, EX exchange, TO transOutput);
}
