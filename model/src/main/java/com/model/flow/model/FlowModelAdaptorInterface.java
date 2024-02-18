package com.model.flow.model;

import com.model.flownode.FlowTransactionConf;

import java.util.Map;

public interface FlowModelAdaptorInterface {

    /**
     * 加载流程定义文件获取模型对象
     * @param isSkip 流程校验失败时是否跳过继续处理
     * @return 流程集合
     */
    Map<String, FlowTransactionConf> getFlowModelMap(boolean isSkip);
}
