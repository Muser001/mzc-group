package com.model.message;

import com.model.message.body.ServiceResponseBody;
import com.model.message.header.ServiceResponseHeader;
import lombok.Data;
import lombok.Generated;


public class ServiceResponseMsg {

    protected ServiceResponseHeader txHeader = new ServiceResponseHeader();

    protected ServiceResponseBody txBody = new ServiceResponseBody();

    @Generated
    public ServiceResponseHeader getTxHeader() {
        return txHeader;
    }

    public void setTxHeader(ServiceResponseHeader txHeader) {
        this.txHeader = txHeader;
    }

    @Generated
    public ServiceResponseBody getTxBody() {
        return txBody;
    }

    public void setTxBody(ServiceResponseBody txBody) {
        this.txBody = txBody;
    }
}
