package com.model.registry.registerhandler;

import com.model.registry.entity.ComposeServiceRegister;

import java.util.Map;

/**
 * 组合服务注册处理器，封装组合服务注册模型暴露服务到注册中心
 */
public interface IComposeServiceRegisterHandler {

    /**
     * 获取已注册的组合服务对象
     * @return
     */
    Map<String, ComposeServiceRegister> getServiceRegisterMap();

    /**
     * 根据组合服务编码获取组合服务对象
     * @return
     */
    ComposeServiceRegister getServiceRegister(String serviceId);
    /**
     * 根据组合服务编码获取组合服务对象
     * @return
     */
    default Class getServiceClazz(String serviceId){return null;}
}
