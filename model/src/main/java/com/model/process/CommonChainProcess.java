package com.model.process;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

/**
 * 责任链处理逻辑
 */
public interface CommonChainProcess {

    /**
     * 责任链执行逻辑
     * @param request  请求报文
     * @param response  响应报文
     */
    void execute(ServiceRequestMsg request, ServiceResponseMsg response);

    /**
     * 业务主体逻辑
     * @param request  请求报文
     * @param response  响应报文
     */
    void invoke(ServiceRequestMsg request, ServiceResponseMsg response);
}
