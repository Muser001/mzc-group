package com.model.transaction;

import com.model.dao.po.SeComposeRunLogPo;
import com.model.log.RunLogDService;
import com.model.message.ServiceRequestMsg;
import com.model.restservice.ServiceCallFactory;
import com.model.restservice.caller.IServiceCaller;
import com.model.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class MainCompensator {

    @Autowired
    private ServiceCallFactory serviceCallFactory;

    @Autowired
    private RunLogDService runLogDService;

    public boolean process(SeComposeRunLogPo txLog) {
        List<SeComposeRunLogPo> subTransactionLogs = runLogDService.queryRunLogByCoreSysSerialNo(
                txLog.getTxDate(), txLog.getCoreSysSeialNo(), txLog.getZoneVal()
        );

        log.info("PCS[{}]开始补偿",txLog.getComposeCode());
        for (SeComposeRunLogPo subLog : subTransactionLogs) {
            boolean result;
            try {
                ServiceRequestMsg serviceRequestMsg = JsonUtils.deserialize(subLog.getPbsReqMsg(),ServiceRequestMsg.class);
                IServiceCaller serviceCaller = serviceCallFactory.getServiceExecutor(false);
                result = serviceCaller.invokeReverse(serviceRequestMsg,99999);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        // TODO: 2024/2/1 先默认成功 
        return true;
    }
}
