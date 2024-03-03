package com.psbc.rm.dom.dao;

import com.psbc.rm.dom.pojo.AtomicLogDo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AtomicLog {
    public void insert(AtomicLogDo atomicLogDo);

    public List<AtomicLogDo> select(String servNo);
}
