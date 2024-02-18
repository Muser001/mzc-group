package com.model.process.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 联机引擎属性配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "")
public class CommonServiceProties {
    /**
     * PBS若同时在BDUS和CDUS上部署，当PBS配置了路由要素但没有上送值时,默认路由到CDUS
     */
    private boolean routeCdusOnMissingRouteValue = true;

    /**
     * 是否分离部署
     */
    private boolean splitDeployment = true;
}
