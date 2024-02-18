package com.model.lock;

import com.model.custinfo.CustInfoBean;
import com.model.message.ServiceRequestMsg;
import com.model.route.RouteInfo;

import java.util.List;

/**
 * 联机引擎分布式事务锁使用服务
 */
public interface DtxLockService {

    /**
     * 异步补偿的加锁信息封装
     * @param serviceRequestMsg 组合服务请求信息
     * @param zoneVal 分库标识
     */
    void buildAsynDistLockRequestMsg(ServiceRequestMsg serviceRequestMsg, String zoneVal);

    /**
     * 异步释放分布式锁
     */
    void unlockDistLock();

    /**
     * 获取分布式事务锁的键
     * @param atomicCode 原子服务编码
     * @param routeRet 路由信息
     * @param custInfoBean 客户信息
     * @return
     */
    String getDxLockKey(String atomicCode, List<RouteInfo> routeRet, CustInfoBean custInfoBean);

    /**
     * 获取分布式事务锁的值
     * @param globalBusiTrackNo 全局业务跟踪号
     * @param subtxSeqNo 子交易流水
     * @param txDate 交易日期
     * @return
     */
    String getDtxLockValue(String globalBusiTrackNo, String subtxSeqNo, String txDate);
}
