package com.model.log;

import com.model.dao.po.SeComposeRunLogPo;
import com.psbc.rm.dom.domain.MyLog;
import com.psbc.rm.dom.pojo.AtomicLogDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RunLogDService {

    @Autowired
    private MyLog myLog;

    /**
     * 独立事务插入RunLog
     * @param po RunLog对象
     * @return 插入数量
     */
    public int doNewTxSaveLog(SeComposeRunLogPo po) {
        return 1;
    }

    /**
     * 插入RunLog
     * @param po RunLog对象
     * @return 插入数量
     */
    public int doTxSaveLog(SeComposeRunLogPo po) {
        return 1;
    }

    /**
     * 反向排序查询RunLog，排除占位
     * @param po RunLog对象
     * @return SeComposeRunLogPo对象集合
     */
    public List<SeComposeRunLogPo> queryRunLogDesc(SeComposeRunLogPo po) {
        return null;
    }

    /**
     * 查询RunLog
     * @return
     */
    public List<SeComposeRunLogPo> queryRunLogByCoreSysSerialNo(SeComposeRunLogPo txLog) {
        // TODO: 2024/2/28 后续加日期，加唯一标识码
        List<SeComposeRunLogPo> subTransactionLogs = new ArrayList<>();
        List<AtomicLogDo> atomicLogDos = myLog.selectLog(txLog.getComposeCode());
        for (AtomicLogDo atomicLogDo : atomicLogDos) {
            SeComposeRunLogPo seComposeRunLogPo = new SeComposeRunLogPo();
            seComposeRunLogPo.setPbsReqMsg(atomicLogDo.getServiceRequestMsg());
            subTransactionLogs.add(seComposeRunLogPo);
        }
        return subTransactionLogs;
    }
}
