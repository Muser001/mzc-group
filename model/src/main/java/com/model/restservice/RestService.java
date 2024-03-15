package com.model.restservice;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;


public interface RestService {

    /**
     * 项目入口
     * @param serviceRequestMsg
     * @return
     */
    ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg);

}
