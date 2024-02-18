package com.model.restservice.caller;

import com.model.context.ThreadLocalContext;
import com.model.context.entity.DbShardingContext;
import com.model.context.entity.ServiceContext;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 本地原子服务调用
 */
@Slf4j
@Component
public class LocalServiceCaller extends ServiceCaller implements IServiceCaller{

    /**
     * 本地服务调用
     */
    @Autowired
    private InnerServiceCaller innerServiceCaller;


    /**
     * 本地调用PBS
     * @param requestMsg 请求对象
     * @param timeout 超时时间
     * @return
     */
    @Override
    public ServiceResponseMsg invokeService(ServiceRequestMsg requestMsg, int timeout) {
        // 保存PCS上下文的引用，方便本地PBS调用后恢复上下文
        ServiceContext pcsServiceContext = ThreadLocalContext.getServiceContext();
        DbShardingContext dbShardingContext = ThreadLocalContext.getDbShardingContext();
        ServiceResponseMsg msg;
        buildPbsContext(requestMsg);

        try {
            msg = innerServiceCaller.invokeService(requestMsg);
        } finally {
            resetPcsContext(pcsServiceContext, dbShardingContext);
        }
        afterExecutorMapper(msg);
        return msg;
    }

    @Override
    public boolean invokeReverse(ServiceRequestMsg requestMsg, int timeout) {
        ServiceContext pcsServiceContext = ThreadLocalContext.getServiceContext();
        DbShardingContext dbShardingContext = ThreadLocalContext.getDbShardingContext();
        buildPbsContext(requestMsg);
        try {
            return innerServiceCaller.invokeReverse(requestMsg);
        } finally {
            resetPcsContext(pcsServiceContext,dbShardingContext);
        }
    }

    /**
     * 构造空的PBS上下文，并使用PBS请求初始化
     * @param serviceRequestMsg 请求对象
     */
    private void buildPbsContext(ServiceRequestMsg serviceRequestMsg) {
        ServiceContext serviceContext = new ServiceContext();
        DbShardingContext dbShardingContext = new DbShardingContext();
        ThreadLocalContext.setServiceContext(serviceContext);
        ThreadLocalContext.setDbShardingContext(dbShardingContext);
        ThreadLocalContext.buildServiceContext(serviceRequestMsg, false);
        ThreadLocalContext.getContext().setExternalService(false);
    }

    /**
     * 重置上下文
     * @param serviceContext PCS上下文
     * @param dbShardingContext PCSDb共享上下文
     */
    private void resetPcsContext(ServiceContext serviceContext, DbShardingContext dbShardingContext) {
        ThreadLocalContext.setServiceContext(serviceContext);
        ThreadLocalContext.setDbShardingContext(dbShardingContext);
        ThreadLocalContext.getContext().setExternalService(false);
    }
}
