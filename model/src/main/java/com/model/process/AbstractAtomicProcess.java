package com.model.process;

import com.model.chain.executor.AtomicChainExecutorImpl;
import com.model.chain.executor.IChainExecutor;
import com.model.chain.manager.IChainStepManager;
import com.model.chain.servicechain.ServiceChainStep;
import com.model.context.ServiceContextHandler;
import com.model.enumtype.EngineBaseEnumType;
import com.model.message.ServiceRequestMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public abstract class AbstractAtomicProcess extends AbstractServiceProcess{

    @Autowired
    @Qualifier("atomicChainStepManagerImpl")
    private IChainStepManager chainStepManager;

    @Autowired
    private AtomicChainExecutorImpl atomicChainExecutor;

    @Override
    protected IChainExecutor getExecutor() {
        return atomicChainExecutor;
    }

    @Override
    protected List<ServiceChainStep> assembleSteps(ServiceRequestMsg serviceRequestMsg) {
        String tag = new StringBuilder().append(serviceRequestMsg.getTxHeader().getProcessType())
                .append("_").append(commonServiceProties.isSplitDeployment() ?
                        EngineBaseEnumType.DeployModeEnum.SPLIT.getValue() :
                        EngineBaseEnumType.DeployModeEnum.COMBINE.getValue()).toString();
        List<ServiceChainStep> chainSteps = chainStepManager.getChainByTag(tag);
        return chainSteps;
    }

    @Override
    protected void afterPreProcess(ServiceRequestMsg serviceRequestMsg) {
        ServiceContextHandler.setEngineInfoContext("PCS_PRE_PROCESS_SUCCESS",true);
    }
}
