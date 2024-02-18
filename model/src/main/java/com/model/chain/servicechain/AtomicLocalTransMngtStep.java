package com.model.chain.servicechain;

import com.model.annotation.AtomicChainStep;
import com.model.chain.constans.ComposeChainStepConstans;
import com.model.context.EngineContextWrapper;
import com.model.enumtype.EngineBaseEnumType;
import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.transaction.LocalTransactionComponent;
import org.springframework.beans.factory.annotation.Autowired;

@AtomicChainStep(order = ComposeChainStepConstans.ATOMIC_LOCAL_TRANS_MNGT_STEP,
            bizRegDirect = {EngineBaseEnumType.BizExeDirectEnum.NORMAL, EngineBaseEnumType.BizExeDirectEnum.COMPENSATE})
public class AtomicLocalTransMngtStep extends AbstractChainStep{

    @Autowired
    private LocalTransactionComponent localTransactionComponent;

    /**
     * 本地事务前处理
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     * @return
     */
    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        if (ServiceType.OUTBOUND.equals(EngineContextWrapper.getAtomicServiceRegister().getServiceType())
            ||OperateType.QUERY == (EngineContextWrapper.getAtomicServiceRegister().getOperateType())) {
            return true;
        }
        localTransactionComponent.openTransaction();
        return true;
    }

    /**
     * 本地事务后处理
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     */
    @Override
    public void postProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        if (ServiceType.OUTBOUND.equals(EngineContextWrapper.getAtomicServiceRegister().getServiceType())
                ||OperateType.QUERY == (EngineContextWrapper.getAtomicServiceRegister().getOperateType())) {
            return ;
        }
        localTransactionComponent.commit();
    }

    @Override
    public void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg, Throwable e) {
        if (ServiceType.OUTBOUND.equals(EngineContextWrapper.getAtomicServiceRegister().getServiceType())
                ||OperateType.QUERY == (EngineContextWrapper.getAtomicServiceRegister().getOperateType())) {
            return ;
        }
        localTransactionComponent.rollback();
    }
}
