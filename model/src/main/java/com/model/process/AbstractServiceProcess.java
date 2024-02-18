package com.model.process;

import com.model.chain.executor.IChainExecutor;
import com.model.process.properties.CommonServiceProties;
import com.model.chain.servicechain.ServiceChainStep;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.transaction.LocalTransactionComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Slf4j
public abstract class AbstractServiceProcess implements CommonChainProcess{

    @Autowired
    protected CommonServiceProties commonServiceProties;

    @Autowired
    private LocalTransactionComponent localTransactionComponent;

    /**
     * 获取实际责任链执行器：PCS责任链执行器、PBS责任链执行器
     * @return
     */
    protected abstract IChainExecutor getExecutor();

    /**
     * PCS和PBS共用这套逻辑
     * @param request  请求报文
     * @param response  响应报文
     */
    @Override
    public void execute(ServiceRequestMsg request, ServiceResponseMsg response) {
        List<ServiceChainStep> steps = assembleSteps(request);
        Deque<ServiceChainStep> stack = new ArrayDeque<>();
        IChainExecutor chainExecutor = getExecutor();
        try {
            if (chainExecutor.preProcess(request,response,steps,stack)){
                afterPreProcess(request);
                invoke(request,response);
                chainExecutor.postProcess(request,response,stack);
            }else {
                localTransactionComponent.commit();
            }
        } catch (Throwable t) {
            log.error("发生异常: ",t);
            //PCS/PBS出现异常虚通知APM
            chainExecutor.exceptionProcess(request,response,t,stack);
        } finally {
            if(localTransactionComponent.hasLocalTransaction()){
                log.error("事务未处理泄露");
            }
        }
    }

    /**
     * 根据封装报文请求类型拼装调用链
     * @param serviceRequestMsg
     * @return
     */
    protected abstract List<ServiceChainStep> assembleSteps(ServiceRequestMsg serviceRequestMsg);

    /**
     * 内置扩展，前处理完成后业务处理前执行
     * @param serviceRequestMsg
     */
    protected abstract void afterPreProcess(ServiceRequestMsg serviceRequestMsg);
}
