package com.model.registry.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ComposeServiceRegister implements Serializable {

    private static final long serialVersionUID = -909313185252156975L;

    /**
     * PCS编排ID
     */
    private String composeCode;

    /**
     * PCS编排中文名称
     */
    private String composeName;

    /**
     * 交易输入DTO路径
     */
    private String innInfDto;

    /**
     * 交易输出DTO
     */
    private String outInfDto;

    /**
     * 针对对交易级配置的事务内外责任链步骤
     */
    private String outTxStep;

    /**
     * 针对对交易级配置的事务内责任链步骤
     */
    private String inTxStep;

    /**
     * 分布式事务标识，true-支持分布式，false-不支持分布式事务
     */
    private boolean txnFlag = false;

    /**
     * mac校验标识,false-不校验，true-校验
     */
    private boolean chkMacFlg = false;

    /**
     * 服务性质，1-查询类，2-维护类，3-财务类
     */
    private Integer operateType = 1;

    //动态配置数据，在配置中心按需配置
    /**
     * 针对对交易级配置mac校验的key属性名称，以,号隔开
     */
    private String macKey;

    /**
     * 组合服务层超时时间
     */
    private Integer maxTxnTime;

    /**
     * 审核标识 false-不审核，true-审核
     */
    private boolean checkFlag = false;

    /**
     * 查询类服务是否登记流水日志
     */
    private boolean queryServiceRegistLog = false;

    /**
     * saf类型，0-不支持saf，1-saf补偿，2-saf重做
     */
    private Integer safSupportType = 0;

    /**
     * saf补偿使用新的交易码
     */
    private String safCompensatorCode = "";

    /**
     * 当前服务是否暴露到注册中心
     */
    private Boolean regist = true;

    /**
     * 冲正服务编号
     */
    private String reverseSevrNo;

    /**
     * 取消服务编号
     */
    private String cancelServNo;

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 交易性质
     */
    private String transProperty;

    /**
     * 组合服务拼装的定义信息
     */
    private String composeDefInfo;

    /**
     * 无分布式事务是未明是否重试
     */
    private Boolean retryWhenUnknownWithoutSaga;

    /**
     * 重做交易码
     */
    private String redoServNo;

    /**
     * 编程式PCS且单PBS模式
     */
    private boolean codeMode = false;

    /**
     * 旧@PCS模式仅支持单个PBS
     */
    private boolean singlePbsMode = false;

























}
