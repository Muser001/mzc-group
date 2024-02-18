package com.model.chain.servicechain;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

/**
 * 预留抽象步骤，组合服务的基础服务同一责任链抽象实现
 */
public class AbstractChainStep implements ServiceChainStep{
    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        return true;
    }

    @Override
    public void postProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {

    }

    @Override
    public void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg, Throwable e) {

    }
}
