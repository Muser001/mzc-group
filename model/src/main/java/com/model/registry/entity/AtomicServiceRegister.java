package com.model.registry.entity;

import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import lombok.Data;

import java.io.Serializable;

@Data
public class AtomicServiceRegister implements Serializable {
    private static final long serialVersionUID = 2951359787764679409L;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 服务中文名称
     */
    private String serviceName;

    /**
     * 服务自身的class类型
     */
    private Class<?> serviceClass;

    /**
     * 服务输入参数类型，InputDto类型
     */
    private Class<?> paramType;

    /**
     * 服务返回参数类型，OutputDto类型
     */
    private Class<?> resultType;

    /**
     * 服务类型，ORDINARY-对外服务，不注册，OUTBOUNO-外呼服务
     */
    private ServiceType serviceType = ServiceType.ORDINARY;

    /**
     * 服务性质
     */
    private OperateType operateType = OperateType.ACCOUNTS;

    /**
     * 动态配置数据，1-加锁，N-不加锁
     */
    private String dynLockFlg = "1";

    /**
     * 当前服务是否暴露到注册中心
     */
    private Boolean regist = true;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 反向冲正超时时间
     */
    private Integer reverseTimeout;

    /**
     * 是否登记子交易流水
     */
    private Boolean registRunLog = true;

    /**
     * 分库路由要素，可取自header或者txEntity中属性
     */
    private String shardingKey;
}
