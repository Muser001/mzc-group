package com.model.context;

import com.model.context.entity.DbShardingContext;
import com.model.context.entity.ServiceContext;
import com.model.message.ServiceRequestMsg;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContext {

    private boolean isExternalService;

    private static ThreadLocal<ThreadLocalContext> LOCAL;
    private Map<String, Object> values = new HashMap<>();

    public ThreadLocalContext() {
        this.values.put("local.service", new ServiceContext());
    }
    public static ServiceContext getServiceContext(){
        return get("local.service") == null ? null : (ServiceContext)get("local.service");
    }
    public static void setServiceContext(ServiceContext serviceContext) {
        put("local.service",serviceContext);
    }
    public static DbShardingContext getDbShardingContext() {
        return get("local.dbSharding") == null ? null : (DbShardingContext)get("local.dbSharding");
    }
    public static void setDbShardingContext(DbShardingContext dbShardingContext) {
        put("local.dbSharding",dbShardingContext);
    }
    public static Object get(String domain){
        return getContext().getValues().get(domain);
    }

    public static void put(String domain, Object object) {
        getContext().getValues().put(domain, object);
    }

    public static ThreadLocalContext getContext(){
        return (ThreadLocalContext) LOCAL.get();
    }

    public Map<String, Object> getValues() {
        return this.values;
    }

    public boolean isExternalService() {
        return isExternalService;
    }

    public void setExternalService(boolean externalService) {
        isExternalService = externalService;
    }

    /**
     * 给ThreadLocalContext赋值，过滤器ContextHandlerFilter赋值的
     * @param requestMsg
     * @param isExternal
     * @return
     */
    public static ServiceContext buildServiceContext(ServiceRequestMsg requestMsg, boolean isExternal) {
        ServiceContext serviceContext = getServiceContext();
        // TODO: 2023/12/9 随便赋值了
        serviceContext.getCommonContext().setTxHeader(requestMsg.getTxHeader());
        setServiceContext(serviceContext);
        return serviceContext;
    }
    static {
        LOCAL = new NamedThreadLocal<ThreadLocalContext>("Local Context"){
            protected ThreadLocalContext initialValue(){
                return new ThreadLocalContext();
            }
        };
    }
}
