package com.example.gateway.serivce.impl;

import com.example.gateway.serivce.api.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 动态路由服务实现
 * RouteDefinitionWriter：提供了对路由的增加删除等操作
 * ApplicationEventPublisher： 是ApplicationContext的父接口之一，他的功能就是发布事件，也就是把某个事件告诉所有与这个事件相关的监听器
 */
@Component
@Slf4j
public class RouteServiceImpl implements RouteService, ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    /**
     * 事件发布器
     */
    @Autowired
    private ApplicationEventPublisher publisher;


    @Override
    public void update(RouteDefinition routeDefinition) {
        log.info("更新路由配置项：{}", routeDefinition);
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void add(RouteDefinition routeDefinition) {
        log.info("新增路由配置项：{}", routeDefinition);
        this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
