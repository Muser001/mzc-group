package com.model.restservice.caller;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

/**
 * 服务引擎-业务入口调用代理
 */
public interface IServiceCaller {

    /**
     * 正常业务
     * @param requestMsg
     * @param timeout
     * @return
     */
    ServiceResponseMsg invokeService(ServiceRequestMsg requestMsg, int timeout);

    /**
     * 反向业务
     * @param requestMsg
     * @param timeout
     * @return
     */
    boolean invokeReverse(ServiceRequestMsg requestMsg, int timeout);
}
