package com.model.dao.po;


import lombok.Data;

//@Table()
@Data
public class SeComposeRunLogPo {

    /**
     * 服务编码
     */
    private String composeCode;

    /**
     * 交易日期
     */
    private String txDate;

    /**
     * 核心系统流水号
     */
    private String coreSysSeialNo;

    /**
     *
     */
    private String zoneVal;

    /**
     * 请求报文
     */
    private String PbsReqMsg;
}
