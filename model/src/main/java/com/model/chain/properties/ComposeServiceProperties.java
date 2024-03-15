package com.model.chain.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "")
public class ComposeServiceProperties {

    /**
     * 是否支持PCS超时检查
     */
    private boolean checkTimeoutSupport = false;

    /**
     * 扫描组合服务包路径
     */
    private String scanComposeServicePakage = "com.psbc.**.pcs.**.impl";
}
