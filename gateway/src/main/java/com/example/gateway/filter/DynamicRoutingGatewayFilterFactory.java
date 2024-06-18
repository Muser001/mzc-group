package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class DynamicRoutingGatewayFilterFactory extends AbstractGatewayFilterFactory<DynamicRoutingGatewayFilterFactory.Config> {


    @Resource
    private DynamicRoutingFilter dynamicRoutingFilter;



    public DynamicRoutingGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {
        // 可选的配置属性
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 这里可以使用config中的参数来定制化过滤器行为
            return dynamicRoutingFilter.filter(exchange, chain); // 假设dynamicRoutingFilter是一个实例
        };
    }

    @Override
    public Config newConfig() {
        return new Config();
    }

    @Override
    public String name() {
        return "DynamicRoutingFilter";
    }
}
