package com.psbc.rm.dom.domain;


import com.psbc.rm.dom.dao.AtomicLog;
import com.psbc.rm.dom.pojo.AtomicLogDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyLog {
    @Autowired
    private AtomicLog atomicLog;

    public void insertLog(AtomicLogDo atomicLogDo) {
        atomicLog.insert(atomicLogDo);
    }
    public List<AtomicLogDo> selectLog(String servNo) {
        return atomicLog.select(servNo);
    }
}
