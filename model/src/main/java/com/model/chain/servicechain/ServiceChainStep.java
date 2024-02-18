package com.model.chain.servicechain;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

public interface ServiceChainStep {

    /**
     * 前处理逻辑
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     * @return
     */
    boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg);

    /**
     * 后处理逻辑
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     */
    void postProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg);

    /**
     * 异常处理逻辑
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     * @param e  异常信息
     */
    void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg, Throwable e);
}
