package com.psbc.rm.dom.service;


import com.psbc.rm.dom.pojo.Test_1;

public interface UserService {
    public Integer getNumber(String name);

    public void updateNumber(Test_1 test_1);

    public void updateTest(String name);
}
