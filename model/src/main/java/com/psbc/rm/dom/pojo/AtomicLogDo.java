package com.psbc.rm.dom.pojo;

import com.model.message.ServiceRequestMsg;
import lombok.Data;

@Data
public class AtomicLogDo {
    private String servNo;
    private String atomicNode;
    private String serviceRequestMsg;
}
