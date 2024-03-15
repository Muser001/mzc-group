package com.model.process;

import com.model.context.EngineContextWrapper;
import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.registerhandler.IAtomicService;
import com.model.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("normalProcess")
public class AtomicNormalProcess extends AbstractAtomicProcess{
    @Override
    public void invoke(ServiceRequestMsg request, ServiceResponseMsg response) {
        IAtomicService atomicService =
                (IAtomicService) ContextUtil.getBean(EngineContextWrapper.getAtomicServiceRegister().getServiceClass());

        BaseInputDTO baseInputDTO = (BaseInputDTO) request.getTxBody().getTxEntity();
        if (log.isDebugEnabled()) {
            log.debug("开始调用-{}自身处理逻辑", request.getTxHeader().getAtomicCode());
        }
        // TODO: 2024/1/9 可以添加前处理和后处理
        BaseOutputDTO baseOutputDTO = (BaseOutputDTO) atomicService.doService(baseInputDTO);

        log.info("结束调用-{}自身处理逻辑", request.getTxHeader().getAtomicCode());

        response.getTxBody().setTxEntity(baseOutputDTO);
    }
}
