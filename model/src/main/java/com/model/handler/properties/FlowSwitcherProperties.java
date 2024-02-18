package com.model.handler.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "")
public class FlowSwitcherProperties {

    /**
     * 远程调用代理bean
     */
    private String invokeProxyBean = "composeServiceNode";
}
