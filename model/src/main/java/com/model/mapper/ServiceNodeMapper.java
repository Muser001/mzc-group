package com.model.mapper;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.dto.FlowExchangeDTO;

/**
 * 服务节点接口，所有节点的mapper都要实现
 * @param <TI>
 * @param <EX>
 * @param <NI>
 * @param <NO>
 */
public interface ServiceNodeMapper <TI extends BaseInputDTO,
        EX extends FlowExchangeDTO,
        NI extends BaseInputDTO,
        NO extends BaseOutputDTO>{

    /**
     * 服务节点执行前输入映射
     * @param transInput 流程输入DTO
     * @param exchange 数据交换DTO
     * @return
     */
    NI inputMapping(TI transInput, EX exchange);

    /**
     * 服务节点执行后输出映射
     * @param atomicOutput 服务输出DTO
     * @param exchange 数据交换DTO
     */
    void outputMapping(NO atomicOutput, EX exchange);


}
