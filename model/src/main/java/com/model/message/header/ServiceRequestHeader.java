package com.model.message.header;


import lombok.Generated;

import java.io.Serializable;

public class ServiceRequestHeader implements Serializable {
    private static final long serialVersionUID = -8462772842460924883L;
    private String servNo;
    private String dusId;
    private String dbId;
    private String tableId;
    private String shardingId;
    private String mappingCusId;
    private String cusId;
    private String txnUsedTime;
    private String mainMapElementInfo;
    private String servTpCd;
    private String processType;
    /**
     * 核心流水号
     */
    private String coreSysSerialNo;
    /**
     * 责任链序号
     */
    private Integer StepSeq;
    /**
     * 原子码
     */
    private String atomicCode;

    @Generated
    public String getServNo() {
        return servNo;
    }
    @Generated
    public void setServNo(String servNo) {
        this.servNo = servNo;
    }
    @Generated
    public String getDusId() {
        return dusId;
    }
    @Generated
    public void setDusId(String dusId) {
        this.dusId = dusId;
    }
    @Generated
    public String getDbId() {
        return dbId;
    }
    @Generated
    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
    @Generated
    public String getTableId() {
        return tableId;
    }
    @Generated
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
    @Generated
    public String getShardingId() {
        return shardingId;
    }
    @Generated
    public void setShardingId(String shardingId) {
        this.shardingId = shardingId;
    }
    @Generated
    public String getMappingCusId() {
        return mappingCusId;
    }
    @Generated
    public void setMappingCusId(String mappingCusId) {
        this.mappingCusId = mappingCusId;
    }
    @Generated
    public String getCusId() {
        return cusId;
    }
    @Generated
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }
    @Generated
    public String getTxnUsedTime() {
        return txnUsedTime;
    }
    @Generated
    public void setTxnUsedTime(String txnUsedTime) {
        this.txnUsedTime = txnUsedTime;
    }
    @Generated
    public String getMainMapElementInfo() {
        return mainMapElementInfo;
    }
    @Generated
    public void setMainMapElementInfo(String mainMapElementInfo) {
        this.mainMapElementInfo = mainMapElementInfo;
    }
    @Generated
    public String getServTpCd() {
        return servTpCd;
    }
    @Generated
    public void setServTpCd(String servTpCd) {
        this.servTpCd = servTpCd;
    }
    @Generated
    public String getProcessType() {
        return processType;
    }
    @Generated
    public void setProcessType(String processType) {
        this.processType = processType;
    }
    @Generated
    public String getCoreSysSerialNo() {
        return coreSysSerialNo;
    }
    @Generated
    public void setCoreSysSerialNo(String coreSysSerialNo) {
        this.coreSysSerialNo = coreSysSerialNo;
    }
    @Generated
    public Integer getStepSeq() {
        return StepSeq;
    }
    @Generated
    public void setStepSeq(Integer stepSeq) {
        StepSeq = stepSeq;
    }
    @Generated
    public String getAtomicCode() {
        return atomicCode;
    }
    @Generated
    public void setAtomicCode(String atomicCode) {
        this.atomicCode = atomicCode;
    }
}
