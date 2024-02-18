package com.model.restservice;

import com.model.restservice.caller.IServiceCaller;
import com.model.restservice.caller.LocalServiceCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceCallFactory {


    @Autowired
    private LocalServiceCaller localServiceCaller;
    /**
     * 获取服务执行器，远程/本地
     * @param isRemoteCall 是否远程调用
     * @return
     */
    public IServiceCaller getServiceExecutor(boolean isRemoteCall) {
        if (isRemoteCall) {
            // TODO: 2023/12/11 后期写远程调用
        }
        return localServiceCaller;
    }
}
