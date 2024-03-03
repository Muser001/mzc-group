package com.model.chain.servicechain;

import com.model.annotation.AtomicChainStep;
import com.model.chain.constans.ComposeChainStepConstans;
import com.model.enumtype.EngineBaseEnumType;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.util.JsonUtils;
import com.psbc.rm.dom.domain.MyLog;
import com.psbc.rm.dom.pojo.AtomicLogDo;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@AtomicChainStep(order = ComposeChainStepConstans.ATOMIC_LOCAL_TRANS_LOG_STEP,
        bizRegDirect = {EngineBaseEnumType.BizExeDirectEnum.NORMAL})
public class AtomicNormalBizLogProcessStep extends AbstractChainStep{
    
    @Autowired
    private MyLog myLog;
    
    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        String serviceRequestMsgStr = null;
        try {
             serviceRequestMsgStr = JsonUtils.serializeToString(serviceRequestMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AtomicLogDo atomicLogDo = new AtomicLogDo();
        atomicLogDo.setServNo(serviceRequestMsg.getTxHeader().getServNo());
        atomicLogDo.setAtomicNode(serviceRequestMsg.getTxHeader().getAtomicCode());
        atomicLogDo.setServiceRequestMsg(serviceRequestMsgStr);
        myLog.insertLog(atomicLogDo);
        // TODO: 2024/2/29 默认成功
        return true;
    }

    @Override
    public void postProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        super.postProcess(serviceRequestMsg, serviceResponseMsg);
    }

    @Override
    public void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg, Throwable e) {
        super.exceptionProcess(serviceRequestMsg, serviceResponseMsg, e);
    }
}
