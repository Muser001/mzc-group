package com.model.restservice.caller;

import com.model.enumtype.EngineBaseEnumType;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.process.CommonChainProcess;
import com.model.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 本地原子服务调用
 */
@Slf4j
@Component
public class InnerServiceCaller {

    private static final String NORMAL_PROCESS = "normalProcess";

    private static final String REVERSE_PROCESS = "reverseProcess";
    /**
     * 调用业务方法
     * @param serviceRequestMsg
     * @return
     */
    public ServiceResponseMsg invokeService(ServiceRequestMsg serviceRequestMsg) {
        serviceRequestMsg.getTxHeader().setProcessType(EngineBaseEnumType.BizExeDirectEnum.NORMAL.getValue());
        return execute(serviceRequestMsg, NORMAL_PROCESS);
    }

    public boolean invokeReverse(ServiceRequestMsg serviceRequestMsg) {
        serviceRequestMsg.getTxHeader().setProcessType(EngineBaseEnumType.BizExeDirectEnum.COMPENSATE.getValue());
        ServiceResponseMsg execute = execute(serviceRequestMsg, REVERSE_PROCESS);
        // TODO: 2024/1/31 先默认成功，后续修改
        return true;
    }

    /**
     *
     * @param serviceRequestMsg 请求信息
     * @param processName 进程名称
     * @return
     */
    private ServiceResponseMsg execute(ServiceRequestMsg serviceRequestMsg, String processName) {
        String atomicCode = serviceRequestMsg.getTxHeader().getAtomicCode();
        ServiceResponseMsg serviceResponseMsg = new ServiceResponseMsg();
        CommonChainProcess process = (CommonChainProcess) ContextUtil.getBean(processName);
        log.info("PBS:[{}][{}]开始执行,请求报文[{}]", atomicCode, processName, serviceRequestMsg);
        process.execute(serviceRequestMsg, serviceResponseMsg);
        log.info("PBS:[{}][{}]执行结束,响应报文[{}]", atomicCode, processName, serviceResponseMsg);
        return serviceResponseMsg;
    }
}
