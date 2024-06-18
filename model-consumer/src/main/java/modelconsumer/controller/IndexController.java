package modelconsumer.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.restservice.RestService;
import modelconsumer.ModelConsumerApplication;
import modelprovider.service.api.ServiceRest;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Reference
    private ServiceRest serviceRest;


    @SentinelResource(value = "service")
    @PostMapping("/service/test")
    public ServiceResponseMsg doInvoke(@RequestBody ServiceRequestMsg serviceRequestMsg) {
        try (Entry service = SphU.entry("service")) {
            return serviceRest.doInvoke(serviceRequestMsg);
        }catch (Exception e) {
            System.out.println(e);
            log.warn("Sentinel 限流了");
        }
        return null;
    }

}
