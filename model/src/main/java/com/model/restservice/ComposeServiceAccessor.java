package com.model.restservice;

import com.model.context.EngineContextWrapper;
import com.model.context.ServiceContextHandler;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.process.CommonChainProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 组合服务访问器，组合服务层的接入处理，作为对通讯协议的适配层
 */
@Slf4j
@Component
public class ComposeServiceAccessor {

    @Autowired
    @Qualifier("composeProcess")
    private CommonChainProcess commonProcess;
    
    @Autowired
    private ApplicationContext applicationContext;

//    @Autowired
//    private IComposeServiceRegisterHandler composeServiceRegisterHandler;

    public ServiceResponseMsg doInvoke(ServiceRequestMsg serviceRequestMsg) {
        // 报文头获取服务组合服务码
        String composeCode = ServiceContextHandler.getTxHeader().getServNo();
        // 初始化核心流水号
        initCoreSysSerialNo(serviceRequestMsg);
        // 初始化PBS步骤号生成器
        initStepCounter();
        // 组合交易责任链处理
        ServiceResponseMsg serviceResponseMsg = new ServiceResponseMsg();
        log.info("PCS [{}] 执行开始，请求报文:[{}]", composeCode, serviceRequestMsg);
        commonProcess.execute(serviceRequestMsg, serviceResponseMsg);
        log.info("PCS [{}] 执行结束，响应报文:[{}]", composeCode, serviceResponseMsg);

        return serviceResponseMsg;
    }

    /**
     * 初始化核心流水号
     * @param serviceRequestMsg
     */
    private void initCoreSysSerialNo(ServiceRequestMsg serviceRequestMsg) {
        // 获取核心流水号
        // TODO: 2023/12/9 后期改成算法生成
        String coreSysSeriaNo = "123456789";
        // 将核心流水号赋值至报文头
        serviceRequestMsg.getTxHeader().setCoreSysSerialNo(coreSysSeriaNo);
        // 将核心流水号赋值至上下文
        ServiceContextHandler.setCoreSysSerialNo(coreSysSeriaNo);
    }

    /**
     * PCS交易入口设置步骤号生成器上下文
     */
    private void initStepCounter() {
        AtomicInteger stepSeq = new AtomicInteger(0);
        AtomicInteger validStepSeq = new AtomicInteger(0);
        EngineContextWrapper.setStepSeqAtomicInteger(stepSeq);
        EngineContextWrapper.setValidStepSeqAtomicInteger(validStepSeq);
    }
}


