package com.model.process;

import com.model.chain.executor.IChainExecutor;
import com.model.chain.manager.IChainStepManager;
import com.model.chain.servicechain.ServiceChainStep;
import com.model.context.EngineContextWrapper;
import com.model.context.ServiceContextHandler;
import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.enumtype.EngineBaseEnumType;
import com.model.flow.FlowHandler;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.ComposeServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 组合服务调用处理，该类主要作用是调用服务编排封装，继承类AbstractServiceProcess，外部入口
 * 调用AbstractServiceProcess.execute执行责任链步骤预检查，前处理之后，执行逻辑invoke
 */
@Component
@Slf4j
public class ComposeProcess extends AbstractServiceProcess{

    @Autowired
    @Qualifier("composeChainStepManagerImpl")
    private IChainStepManager chainStepManager;

    @Autowired
    @Qualifier("composeChainExecutor")
    private IChainExecutor composeChainExecutor;

    @Autowired
    private FlowHandler flowHandler;

//    @Autowired
//    private ComposeServiceNode composeServiceNode;

    @Override
    protected IChainExecutor getExecutor() {
        return composeChainExecutor;
    }

    @Override
    protected List<ServiceChainStep> assembleSteps(ServiceRequestMsg serviceRequestMsg) {
        String servTpCd = serviceRequestMsg.getTxHeader().getServTpCd();

        //不在枚举里的交易类型当正交易处理
        if (EngineBaseEnumType.ServTypeCodeType.toEnum(servTpCd) == null) {

        }

        String tag = new StringBuilder().append(servTpCd)
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

    /**
     * 服务调用逻辑主体，用于调用编排服务，执行抽象类AbstractServiceProcess.execute后执行该方法
     * @param serviceRequestMsg  请求报文
     * @param serviceResponseMsg  响应报文
     */
    @Override
    public void invoke(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        String composeCode = EngineContextWrapper.getProcessServNo();
        log.info("当前服务编码：{}，调用的服务编排ID：{}",serviceRequestMsg.getTxBody().getTxEntity());
        BaseInputDTO inputDTO = (BaseInputDTO) serviceRequestMsg.getTxBody().getTxEntity();

        ComposeServiceRegister composeServiceRegister = new ComposeServiceRegister();
        BaseOutputDTO outputDTO = null;
        if (composeServiceRegister.isCodeMode()) {

        } else {
            outputDTO = flowHandler.callFlow(composeCode, inputDTO);
//            composeServiceNode.process(composeService.getAtomicCode(),"BDUS", composeService.isKeyNode()
//                    "SUCESS",inputDTO);
        }

        serviceResponseMsg.getTxBody().setTxEntity(outputDTO);
    }
}
