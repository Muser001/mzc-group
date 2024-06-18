package com.model.restservice;


import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;


@Service
public class RestServiceImpl implements RestService{

    @Resource
    private ComposeServiceAccessor invoker;

    @Override
    public ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg) {
        return this.invoker.doInvoke(serviceRequestMsg);
    }

}
