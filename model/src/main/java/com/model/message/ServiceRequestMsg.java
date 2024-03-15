package com.model.message;

import com.model.message.body.ServiceRequestBody;
import com.model.message.header.ServiceRequestHeader;
import lombok.Generated;

import java.io.Serializable;


public class ServiceRequestMsg implements Serializable {
    private static final long serialVersionUID = -9061298923699705290L;
    protected String reqMsg;
    protected ServiceRequestHeader txHeader = new ServiceRequestHeader();
    protected ServiceRequestBody txBody = new ServiceRequestBody();
    @Generated
    public String getReqMsg() {
        return reqMsg;
    }
    @Generated
    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }
    @Generated
    public ServiceRequestHeader getTxHeader() {
        return txHeader;
    }
    @Generated
    public void setTxHeader(ServiceRequestHeader txHeader) {
        this.txHeader = txHeader;
    }
    @Generated
    public ServiceRequestBody getTxBody() {
        return txBody;
    }
    @Generated
    public void setTxBody(ServiceRequestBody txBody) {
        this.txBody = txBody;
    }
}
