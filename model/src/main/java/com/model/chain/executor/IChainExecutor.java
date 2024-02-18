package com.model.chain.executor;

import com.model.chain.servicechain.ServiceChainStep;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;

import java.util.Deque;
import java.util.List;

public interface IChainExecutor {

    /**
     * 调用链前处理，按照Order从小到大顺序执行，返回true继续执行，返回false则中断执行
     * @param serviceRequestMsg
     * @param serviceResponseMsg
     * @param steps
     * @param stack
     * @return
     */
    boolean preProcess(ServiceRequestMsg serviceRequestMsg,
                       ServiceResponseMsg serviceResponseMsg,
                       List<ServiceChainStep> steps,
                       Deque<ServiceChainStep> stack);

    /**
     * 调用链后处理，按照Order从大到1小顺序执行，后处理在PCS服务编排flow执行完后再执行
     * @param serviceRequestMsg
     * @param serviceResponseMsg
     * @param stack
     */
    void postProcess(ServiceRequestMsg serviceRequestMsg,
                     ServiceResponseMsg serviceResponseMsg, Deque<ServiceChainStep> stack);

    /**
     * 调用链异常处理，preProcess、flow、postProcess抛异常执行exceptionProcess，从当前报错的Step位置往前逆行执行
     * @param serviceRequestMsg
     * @param serviceResponseMsg
     * @param e
     * @param stack
     */
    void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg,
                          Throwable e, Deque<ServiceChainStep> stack);

}
