package com.model.util;

import com.model.message.ServiceRequestMsg;
import org.apache.dubbo.rpc.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvocationUtil {
    private static final Logger logger = LoggerFactory.getLogger(InvocationUtil.class);

    public static ServiceRequestMsg getServiceRequestMsgIncludeCastAndAssignment(Invocation invocation) {
        return getServiceRequest(invocation, true, true);
    }

    public static ServiceRequestMsg getServiceRequest(Invocation invocation, boolean isCast, boolean isAssigment) {
        ServiceRequestMsg requestMsg = null;
        if ("$incoke".equals(invocation.getMethodName())) {
            // TODO: 2023/12/23 没想好咋写
        }else if (invocation.getArguments() != null && invocation.getArguments().length > 0 && invocation.getArguments()[0] instanceof ServiceRequestMsg) {
            requestMsg = (ServiceRequestMsg) invocation.getArguments()[0];
        }
        return requestMsg;
    }
}
