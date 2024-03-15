package com.model.flow.model.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
//@ConfigurationProperties(prefix = "")
@Configuration
public class FlowResourcesClasspathProperties {
    private String path = "classpath*:flow/**/*.flow";
}
