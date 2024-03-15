package com.model.chain.servicechain;

import com.model.annotation.AtomicChainStep;
import com.model.chain.constans.AtomicChainStepConstans;
import com.model.context.EngineContextWrapper;
import com.model.enumtype.EngineBaseEnumType;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.registry.registerhandler.AtomicServiceRegisterHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@AtomicChainStep(order = AtomicChainStepConstans.ATOMIC_TIME_OUT_INIT_STEP,
                bizRegDirect = {EngineBaseEnumType.BizExeDirectEnum.NORMAL,
                        EngineBaseEnumType.BizExeDirectEnum.COMPENSATE,
                        EngineBaseEnumType.BizExeDirectEnum.QUERY})
public class AtomicTimeOutInitStep extends AbstractChainStep{

    @Autowired
    private AtomicServiceRegisterHandler atomicServiceRegisterHandler;


    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        AtomicServiceRegister atomicServiceRegister =
                loadServiceRegister(serviceRequestMsg.getTxHeader().getAtomicCode());

        // TODO: 2024/1/10 后续设置PBS的超时时间
        return true;
    }

    protected AtomicServiceRegister loadServiceRegister(String atomicCode) {
        AtomicServiceRegister atomicServiceRegister = atomicServiceRegisterHandler.getServiceRegister(atomicCode);
        if (atomicServiceRegister == null) {
            log.error("通过PBS交易码{}，获取PBS定义信息失败", atomicCode);
            throw new RuntimeException("通过PBS交易码{" + atomicCode +"}，获取PBS定义信息失败");
        }
        EngineContextWrapper.setAtomicServiceRegister(atomicServiceRegister);
        return atomicServiceRegister;
    }
}
