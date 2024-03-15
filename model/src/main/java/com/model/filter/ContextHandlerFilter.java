package com.model.filter;

import com.model.context.ThreadLocalContext;
import com.model.message.ServiceRequestMsg;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Activate(
        group = {"provider"},
        order = -8500
)
public class ContextHandlerFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ContextHandlerFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ServiceRequestMsg requestMsg = null;
        //requestMsg = (ServiceRequestMsg) RpcContext.getContext().getObjectAttachment("currentRequest");
        requestMsg = (ServiceRequestMsg) RpcContext.getContext().get("currentRequest");
        logger.info("currentRequest before invoking the service: {}", requestMsg);
        if (requestMsg != null) {
            ThreadLocalContext.buildServiceContext(requestMsg,false);
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
