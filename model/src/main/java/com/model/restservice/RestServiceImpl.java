package com.model.restservice;


import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;


@DubboService
public class RestServiceImpl implements RestService{

    @Resource
    private ComposeServiceAccessor invoker;

    @Override
    public ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg) {
        return this.invoker.doInvoke(serviceRequestMsg);
    }

}
