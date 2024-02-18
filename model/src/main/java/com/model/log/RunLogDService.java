package com.model.log;

import com.model.dao.po.SeComposeRunLogPo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RunLogDService {

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
     * 反向排序查询RunLog
     * @param txDate 交易日期
     * @param coreSysSerialNo 核心流水号
     * @param zoneVal
     * @return
     */
    public List<SeComposeRunLogPo> queryRunLogByCoreSysSerialNo(String txDate, String coreSysSerialNo, String zoneVal) {
        return null;
    }
}
