package modelprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.restservice.RestService;
import modelprovider.service.api.ServiceRest;

import javax.annotation.Resource;

@Service(protocol = {"rest"})
public class ServiceRestImpl implements ServiceRest {

    @Resource
    private RestService restService;

    @Override
    public ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg) {
        return restService.doInvoke(serviceRequestMsg);
    }
}
