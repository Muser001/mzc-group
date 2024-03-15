package com.model.chain.servicechain;

import com.model.annotation.ComposeChainStep;
import com.model.chain.constans.ComposeChainStepConstans;
import com.model.chain.properties.ComposeServiceProperties;
import com.model.context.EngineContextWrapper;
import com.model.dao.po.SeComposeRunLogPo;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.ComposeServiceRegister;
import com.model.registry.registerhandler.IComposeServiceRegisterHandler;
import com.model.transaction.MainCompensator;
import com.model.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 数据初始化，后期加上防重，分布式事务等的判断
 */
@Slf4j
@ComposeChainStep(order = ComposeChainStepConstans.COMPOSE_TXNLOG_PRE_STEP)
public class ComposePreTxnLogProcess extends AbstractChainStep{

    @Autowired
    private ComposeServiceProperties commonProperties;

    @Autowired
    private IComposeServiceRegisterHandler composeServiceRegisterHandler;

    @Autowired
    private MainCompensator compensator;

    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg){
        ComposeServiceRegister serviceRegister = loadServiceRegister(serviceRequestMsg.getTxHeader().getServNo());
        EngineContextWrapper.setComposeServiceRegister(serviceRegister);
        // 登记日志
        SeComposeRunLogPo seComposeRunLogPo = new SeComposeRunLogPo();
        String str = null;
        try {
            str = JsonUtils.serializeToStringSensitive(serviceRequestMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        seComposeRunLogPo.setPbsReqMsg(str);
        seComposeRunLogPo.setComposeCode(serviceRequestMsg.getTxHeader().getServNo());
        EngineContextWrapper.setSeComposeRunLogPo(seComposeRunLogPo);
        return true;
    }

    @Override
    public void postProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {
        super.postProcess(serviceRequestMsg, serviceResponseMsg);
    }

    @Override
    public void exceptionProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg, Throwable e) {
        SeComposeRunLogPo seComposeRunLogPo = EngineContextWrapper.getSeComposeRunLogPo();
        if (rollback(seComposeRunLogPo)) {

        }
    }

    /**
     * 加载组合服务注册模型
     * @param composeCode
     * @return
     */
    private ComposeServiceRegister loadServiceRegister(String composeCode) {
        // TODO: 2024/1/29 后期修改线程id 
        EngineContextWrapper.setIndexLocal((int) Thread.currentThread().getId());
        ComposeServiceRegister serviceRegister = composeServiceRegisterHandler.getServiceRegister(composeCode);
        if (serviceRegister == null) {
            if (log.isDebugEnabled()) {
                log.debug("交易[{}]未定义或上送服务码错误", composeCode);
            }
            throw new RuntimeException("交易[" + composeCode + "]未定义或上送服务码错误");
        }
        return serviceRegister;
     }

    /**
     * 即时补偿逻辑
     * @param mainTxLog 主流水登记Po
     * @return 是否即时补偿逻辑
     */
     private boolean rollback(SeComposeRunLogPo mainTxLog) {
        boolean rollbackResult = false;
        String composeCode = mainTxLog.getComposeCode();
         try {
             rollbackResult = compensator.process(mainTxLog);
         } catch (Exception e) {
             log.error("使用补偿器[{}],为交易[{}]进行反向补偿失败：",compensator,composeCode,e);
         }
         return rollbackResult;
     }
}
