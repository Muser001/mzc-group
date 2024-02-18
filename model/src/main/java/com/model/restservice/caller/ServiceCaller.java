package com.model.restservice.caller;

import com.model.message.ServiceResponseMsg;

/**
 * 服务调用基类
 */
public class ServiceCaller {

    /**
     * 原子服务调用后把共享域信息映射到当前上下文
     * @param serviceResponseMsg
     */
    protected void afterExecutorMapper(ServiceResponseMsg serviceResponseMsg) {

    }
}
