package com;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.restservice.RestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ConsumerApplication {

//    private static final Logger log = LoggerFactory.getLogger(ConsumerApplication.class);
//    public static void main(String[] args) {
//        SpringApplication.run(ConsumerApplication.class, args);
//    }
//    @RestController
//    public static class IndexController {
//        @DubboReference
//        private RestService restService;
//
//
//        @SentinelResource(value = "service")
//        @PostMapping("/service/test")
//        public ServiceResponseMsg doInvoke(@RequestBody ServiceRequestMsg serviceRequestMsg) {
//            try (Entry service = SphU.entry("service")) {
//                return restService.doInvoke(serviceRequestMsg);
//            }catch (Exception e) {
//                log.warn("Sentinel 限流了");
//            }
//            return null;
//        }
//
//    }
}
