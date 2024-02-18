package com.model.registry.registerhandler;

import com.model.registry.entity.AtomicServiceRegister;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 基础服务注册处理器，封装组基础服务注册模型，暴露服务到注册中心
 */
public interface IAtomicServiceRegisterHandler {

    /**
     * 获取内存中的基础服务模型
     * @return
     */
    Map<String, AtomicServiceRegister> getServiceRegisterMap();

    /**
     * 根据服务ID，获取内存中的基础服务注册模型
     * @param serviceId 服务ID
     * @return 基础服务注册模型
     */
    AtomicServiceRegister getServiceRegister(String serviceId);

}
