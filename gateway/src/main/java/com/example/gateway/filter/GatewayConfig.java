package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * RouteLocator: 是Spring Cloud Gateway用于静态配置路由的主要方式，通过RouteLocatorBuilder构建路由规则。
 * 在您的GatewayConfig类中，您使用了RouteLocatorBuilder来定义了一个静态的路由到ai-product服务。
 */
@Configuration
public class GatewayConfig {


    @Value("${server.port}")
    private String port;


    @Resource
    private RouteDefinitionLocator routeDefinitionLocator;
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(r -> r
//                        // 断言（判断条件）
//                        .path("/product/**")
//                        // 目标 URI，路由到微服务的地址
//                        .uri("lb://ai-product")
//                        // 注册自定义网关过滤器
//                        .filters(new DynamicRoutingFilter())
//                        // 路由 ID，唯一
//                        .id("product"))
//                .build();
        return null;
    }
}
