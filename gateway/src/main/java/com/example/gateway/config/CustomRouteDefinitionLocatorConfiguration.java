package com.example.gateway.config;

import com.example.gateway.filter.DynamicRoutingFilter;
import com.example.gateway.filter.DynamicRoutingGatewayFilterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RouteDefinitionLocator: 主要用于动态地从外部配置源（如数据库、配置中心）加载路由定义信息。
 * 在您的CustomRouteDefinitionLocatorConfiguration类中，您通过实现RouteDefinitionLocator接口，基于DiscoveryClient动态生成路由规则。
 */
@Configuration
@Slf4j
public class CustomRouteDefinitionLocatorConfiguration {

    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private DynamicRoutingGatewayFilterFactory dynamicRoutingGatewayFilterFactory;

    @Bean
    public RouteDefinitionLocator customRouteDefinitionLocator() {
        return new RouteDefinitionLocator() {
            @Override
            public Flux<RouteDefinition> getRouteDefinitions() {
                discoveryClient.getServices().forEach(service ->{
                    log.info("RouteDefinitionLocator配置动态路由时获取的注册信息{}",service);
                });

                List<RouteDefinition> definitions = new ArrayList<>();
                // 这里添加逻辑来遍历Nacos中的服务实例，并根据您的复合服务名格式解析
                // 然后构造RouteDefinition对象加入到definitions列表中

                discoveryClient.getServices().forEach(serviceName -> {
                    if (serviceName.startsWith("providers:")) {
                        // 示例逻辑：简单展示如何处理，实际情况可能需要更复杂的逻辑来解析和创建RouteDefinition
                        //String logicalServiceName = serviceName.replace("providers:", ""); // 假设逻辑服务名为去掉前缀的部分
                        String logicalServiceName = "model_1";
                        RouteDefinition routeDefinition = new RouteDefinition();
                        routeDefinition.setId(logicalServiceName + "_route");
                        // 注意：这里的uri需要根据实际情况调整，比如使用负载均衡的服务ID或具体的地址
                        routeDefinition.setUri(URI.create("lb://" + logicalServiceName));

                        // 添加路由谓词，例如基于路径的匹配
                        Map<String, String> predicateArgs = new HashMap<>();
                        predicateArgs.put("pattern", "/service/doInvoke");
                        PredicateDefinition predicateDefinition = new PredicateDefinition();
                        predicateDefinition.setName("Path");
                        predicateDefinition.setArgs(predicateArgs);
                        routeDefinition.getPredicates().add(predicateDefinition);
                        // 自定义过滤器
//                    Map<String, String> filterArgs = new HashMap<>();
//                    filterArgs.put("name", "dynamicRoutingFilter");
//                    FilterDefinition filterDefinition = new FilterDefinition();
//                    filterDefinition.setName(DynamicRoutingFilter.class.getName());
//                    filterDefinition.setArgs(filterArgs);
//                    routeDefinition.getFilters().add(filterDefinition);
                        FilterDefinition filterDefinition = new FilterDefinition("DynamicRoutingFilter");
                        routeDefinition.getFilters().add(filterDefinition);
                        definitions.add(routeDefinition);
                    }
                });

                // 打印路由定义到日志
                definitions.forEach(route -> {
                    log.info("RouteDefinitionLocator动态路由配置 {}", route);
                });
                return  Flux.fromIterable(definitions);
            }

        };

    }
}
