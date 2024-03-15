package com.model.filter;

import com.model.message.ServiceRequestMsg;
import com.model.util.InvocationUtil;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Activate(
        group = {"provider"},
        order = -11500
)
public class RequestMessageParseFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestMessageParseFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        try {
            ServiceRequestMsg requestMsg = InvocationUtil.getServiceRequestMsgIncludeCastAndAssignment(invocation);
            if (requestMsg != null) {
                RpcContext.getContext().set("currentRequest", requestMsg);
                logger.info("Setting currentRequest in RpcContext");

            } else if (logger.isDebugEnabled()) {
                logger.debug("requestMsg 为 空");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Result result;
        try {
            result = invoker.invoke(invocation);
        } catch (RpcException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
