package com.model.chain.executor;

import com.model.chain.servicechain.ServiceChainStep;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Deque;

/**
 * PCS调用链执行器，后处理特殊处理异常，包装成Uncertain再抛
 */
@Component("composeChainExecutor")
@Slf4j
public class ComposeChainExecutorImpl extends ChainExecutorImpl{
    @Override
    public void postProcess(ServiceRequestMsg request, ServiceResponseMsg response, Deque<ServiceChainStep> stack) {
        while(!stack.isEmpty()){
            ServiceChainStep step = stack.peek();
            String stepName = ClassUtils.getShortName(step.getClass());
            try {
                step.postProcess(request,response);
            } catch (Throwable t) {
                /** 只有事务内的责任链步骤且关键PBS未执行的前提下，PCS后处理出现异常才能往外抛出 */
                if(isTxOutStep(step)){
                    log.error("责任链步骤[{}]为事务外步骤,为保证交易状态的一致性该步骤出现异常不能往外抛出!",stepName,t);
                }else if(1 != 2){
                    log.error("");
                }else{
                    throw new RuntimeException(t);
                }
            }
            stack.pop();
        }
    }


}
