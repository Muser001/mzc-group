package com.model.context;

import com.model.context.entity.ServiceContext;
import com.model.context.entity.SharedContext;
import com.model.message.ServiceCommonRequestMsg;
import com.model.message.header.ServiceRequestHeader;

public class ServiceContextHandler {

    /**
     * 获取共享上下文-只在本类中能获取
     */
    public static ServiceCommonRequestMsg getTxCommon() {
        return ThreadLocalContext.getServiceContext().getSharedContext().getTxCommon();
    }
    /**
     * 获取报文头
     */
    public static ServiceRequestHeader getTxHeader() {
        return ThreadLocalContext.getServiceContext().getCommonContext().getTxHeader();
    }

    /**
     * 获取主客户所在的《单元ID》
     * @return
     */
    public static String getDusId() {
        return getTxHeader().getDusId();
    }

    /**
     * 获取主客户所在的《分库ID》
     * @return
     */
    public static String getDbId() {
        return getTxHeader().getDbId();
    }

    /**
     * 获取主客户所在的《分表ID》
     * @return
     */
    public static String getTableId() {
        return getTxHeader().getTableId();
    }

    /**
     * 获取主客户所在的《分片ID》
     * @return
     */
    public static String getShardingId() {
        return getTxHeader().getShardingId();
    }

    /**
     * 获取主客户的《映射客户id》
     * @return
     */
    public static String getMappingCusId() {
        return getTxHeader().getMappingCusId();
    }

    /**
     * 获取主客户的《客户ID》
     * @return
     */
    public static String getCusId() {
        return getTxHeader().getCusId();
    }

    /**
     * 私有域上下文获取传递的值
     * @param key
     * @return
     */
    public static Object getPrivateContextInfo(String key) {
        return ThreadLocalContext.getServiceContext().getPrivateContext().get(key);
    }

    /**
     * 私有域上下文传递值
     * @param key
     * @param value
     */
    public static void putPrivateContextInfo(String key, Object value) {
        ThreadLocalContext.getServiceContext().getPrivateContext().put(key,value);
    }

    /**
     * 获取公共报文头中的客户《主映射要素》
     * @return
     */
    public static String getMainElementInfo() {
        return getTxHeader().getMainMapElementInfo();
    }

    /**
     * 获取公共报文头中的《服务编码》
     * @return
     */
    public static String getServNo() {
        return getTxHeader().getServNo();
    }

    /**
     * 设置引擎公共上下文
     */
    public static void setEngineInfoContext(String key, Object value) {
        getSharedContext().getTxCommon().getEngineInfo().put(key, value);
    }

    /**
     * 获取共享上下文-只在类中获取
     */
    public static SharedContext getSharedContext() {
        return ThreadLocalContext.getServiceContext().getSharedContext();
    }

    /**
     * 获取联机引擎上下文
     */
    public static ServiceContext getServiceContext() {
        return ThreadLocalContext.getServiceContext();
    }
    /**
     * 赋值核心流水号
     */
    public static void setCoreSysSerialNo(String coreSysSerialNo) {
        getTxHeader().setCoreSysSerialNo(coreSysSerialNo);
    }
}
