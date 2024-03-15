package com.model.configuration;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfiguration {

    @Bean
    public IClientConfig iClientConfig() {
        return new DefaultClientConfigImpl();
    }
}
