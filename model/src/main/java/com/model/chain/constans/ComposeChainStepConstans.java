package com.model.chain.constans;


/**
 * 定义组合服务责任链的序号的常量
 */
public class ComposeChainStepConstans {

    /**
     * 责任链步骤：数据初始化
     */
    public static final int COMPOSE_TXNLOG_PRE_STEP = -6000;

    /**
     * 责任链步骤：元数据校验
     */
    public static final int COMPOSE_METADATA_VERF_STEP = -4000;

    /**
     * 责任链步骤：本地事务管理
     */
    public static final int ATOMIC_LOCAL_TRANS_MNGT_STEP = 0;

    /**
     * 责任链步骤：基础服务日志
     */
    public static final int ATOMIC_LOCAL_TRANS_LOG_STEP = 1000;
}
