package com.psbc.rm.dom.dao;



import com.psbc.rm.dom.pojo.Test_1;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Integer getNumber(String name);

    public void updateNumber(Test_1 test_1);
}
