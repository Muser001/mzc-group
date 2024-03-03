package com.model.process;

import com.model.context.EngineContextWrapper;
import com.model.dto.BaseOutputDTO;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.registry.registerhandler.IAtomicService;
import com.model.util.ContextUtil;
import com.model.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component("reverseProcess")
public class AtomicReverseProcess extends AbstractAtomicProcess{
    @Override
    public void invoke(ServiceRequestMsg request, ServiceResponseMsg response) {
        // 获取PBS注册模型
        AtomicServiceRegister atomicServiceRegister = EngineContextWrapper.getAtomicServiceRegister();

        // 获取inputDTO
        Map<String, Object> txEntity = (Map<String, Object>) request.getTxBody().getTxEntity();
        String txEntityStr = null;
        Object inputDTO = null;
        try {
            txEntityStr = JsonUtils.serializeToString(txEntity);
            inputDTO = JsonUtils.deserialize(txEntityStr, atomicServiceRegister.getParamType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 执行反向交易
        IAtomicService atomicService = (IAtomicService) ContextUtil.getBean(atomicServiceRegister.getServiceClass());
        BaseOutputDTO baseOutputDTO = (BaseOutputDTO) atomicService.compensate(inputDTO);
        response.getTxBody().setTxEntity(baseOutputDTO);

        String a ="121231";
    }
}
