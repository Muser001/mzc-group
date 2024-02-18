package com.model.custinfo;


import lombok.Generated;

import java.io.Serializable;

public class CustInfoBean implements Serializable {
    protected String mappingCusId;
    protected String cusId;
    protected String shardingId;
    protected String dbId;
    protected String dusId;
    protected String tableId;
    protected String routeType;
    protected String routeKey;
    private String atomicCode;
    private boolean remoteCall;
    private String crossDbFlag;

    @Generated
    public CustInfoBean() {

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
    public String getShardingId() {
        return shardingId;
    }
    @Generated
    public void setShardingId(String shardingId) {
        this.shardingId = shardingId;
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
    public String getDusId() {
        return dusId;
    }
    @Generated
    public void setDusId(String dusId) {
        this.dusId = dusId;
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
    public String getRouteType() {
        return routeType;
    }
    @Generated
    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }
    @Generated
    public String getRouteKey() {
        return routeKey;
    }
    @Generated
    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
    @Generated
    public String getAtomicCode() {
        return atomicCode;
    }
    @Generated
    public void setAtomicCode(String atomicCode) {
        this.atomicCode = atomicCode;
    }
    @Generated
    public boolean isRemoteCall() {
        return remoteCall;
    }
    @Generated
    public void setRemoteCall(boolean remoteCall) {
        this.remoteCall = remoteCall;
    }
    @Generated
    public String getCrossDbFlag() {
        return crossDbFlag;
    }
    @Generated
    public void setCrossDbFlag(String crossDbFlag) {
        this.crossDbFlag = crossDbFlag;
    }
}
