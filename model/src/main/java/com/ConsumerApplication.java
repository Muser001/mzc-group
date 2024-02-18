package com;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.restservice.RestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
    @RestController
    public static class IndexController {
        @DubboReference
        private RestService restService;


        @PostMapping("/service/test")
        public ServiceResponseMsg doInvoke(@RequestBody ServiceRequestMsg serviceRequestMsg) {
            return restService.doInvoke(serviceRequestMsg);
        }

    }
}
