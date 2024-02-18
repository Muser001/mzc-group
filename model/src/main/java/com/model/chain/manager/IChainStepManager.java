package com.model.chain.manager;

import com.model.chain.servicechain.ServiceChainStep;

import java.util.List;

public interface IChainStepManager {

    /**
     * 返回指定tag类型的Step链
     * @param tag 交易方向_部署模式
     * @return List
     */
    List<ServiceChainStep> getChainByTag(String tag);
}
