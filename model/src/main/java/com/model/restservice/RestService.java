package com.model.restservice;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


public interface RestService {

    /**
     * 项目入口
     * @param serviceRequestMsg
     * @return
     */
    ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg);

}
