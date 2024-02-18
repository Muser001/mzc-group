package com.model.dto;

public interface IComposeService <T extends BaseInputDTO, R extends BaseOutputDTO>{

    /**
     * 执行组合服务处理
     * @param inputDTO
     * @return
     */
    default R process(T inputDTO) {
        return null;
    }

    /**
     * 含有单个PBS的PCS返回PBS的原子服务码
     * @return
     */
    default String getAtomicCode() {
        return null;
    }

    /**
     * 是否是关键节点
     * @return
     */
    default boolean isKeyNode() {
        return false;
    }

}
