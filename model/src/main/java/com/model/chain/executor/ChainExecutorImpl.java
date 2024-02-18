package com.model.chain.executor;


import com.model.annotation.AtomicChainStep;
import com.model.annotation.ComposeChainStep;
import com.model.chain.servicechain.ServiceChainStep;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;

import java.util.Deque;
import java.util.List;

@Slf4j
@Getter
public abstract class ChainExecutorImpl implements IChainExecutor{

    @Override
    public boolean preProcess(ServiceRequestMsg request, ServiceResponseMsg response, List<ServiceChainStep> steps, Deque<ServiceChainStep> stack) {
        if(!steps.isEmpty()){
            for(int i = 0; i < steps.size(); i++){
                stack.push(steps.get(i));
                ServiceChainStep step = steps.get(i);
                boolean result = step.preProcess(request,response);
                if(!result){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void exceptionProcess(ServiceRequestMsg request, ServiceResponseMsg response,
                                 Throwable e, Deque<ServiceChainStep> stack) {
        while(!stack.isEmpty()){
            try {
                ServiceChainStep step = stack.pop();
                step.exceptionProcess(request,response,e);
            } catch (Throwable t) {
                log.warn("调用链异常处理吞掉异常");
                e.addSuppressed(t);
            }
        }
    }

    protected boolean isTxOutStep(ServiceChainStep chainStep){
        Class<? extends ServiceChainStep> stepClass =
                (Class<? extends ServiceChainStep>) ClassUtils.getUserClass(chainStep.getClass());
        if(stepClass.isAnnotationPresent(ComposeChainStep.class)){
            ComposeChainStep stepAnn = stepClass.getAnnotation(ComposeChainStep.class);
            return stepAnn.order() < 0;
        }else if(stepClass.isAnnotationPresent(AtomicChainStep.class)){
            AtomicChainStep stepAnn = stepClass.getAnnotation(AtomicChainStep.class);
            return stepAnn.order() < 0;
        }else{
            String stepName = ClassUtils.getShortName(stepClass);
            log.warn("该责任链步骤[{}]添加的注解标识不属于@ComposeChainStep或@AtomicChainStep，请检查" ,stepName);
        }
        return false;
    }
}
