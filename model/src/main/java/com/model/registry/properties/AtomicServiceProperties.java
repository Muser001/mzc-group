package com.model.registry.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "")
@Configuration
public class AtomicServiceProperties {

    /** 扫描atomic基础服务的接口的包路径 */
    private String scanAtomicInterfacePackage = "com.psbc.rm.**.pbs.api";
}
