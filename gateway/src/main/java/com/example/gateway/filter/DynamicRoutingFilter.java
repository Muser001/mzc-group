package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class DynamicRoutingFilter implements GatewayFilter, Ordered {

    private static final String PROVIDERS_PREFIX = "providers:com.model.restservice.RestService";

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String path = request.getPath().value(); // 获取请求路径
        Flux<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions();
        // 假设路径结构是 /{serviceId}/doInvoke，这里简化处理
        String serviceId = path.split("/")[1]; // 简单示例，实际情况可能需要更复杂的解析逻辑

        // 构建新的Uri，格式为 lb://providers:com.model.restservice.RestService::
//        String targetUri = "lb://" + PROVIDERS_PREFIX  + "::";
        String targetUri = "providers:com.model.restservice.RestService::";
        // 修改请求的目标Uri
        ServerHttpRequest newRequest = request.mutate().uri(URI.create(targetUri)).build();

        // 构建新的交换体
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        // 继续执行后续过滤器链
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // 设置优先级，确保此过滤器先于其他过滤器执行
    }
}
