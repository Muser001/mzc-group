package com.model.registry.online;


import com.model.registry.registerhandler.AtomicServiceRegisterHandler;
import com.model.registry.MdpConfig;
import com.model.registry.properties.AtomicServiceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "online", name = "enabled", matchIfMissing = true, havingValue = "true")
@EnableConfigurationProperties({})
public class OnlineServiceAutoConfiguration {

    @Bean
    public AtomicServiceRegisterHandler AtomicServiceRegisterHandler(AtomicServiceProperties atomicServiceProperties, MdpConfig mdpConfig){
        return new AtomicServiceRegisterHandler(atomicServiceProperties,mdpConfig);
    }
}
