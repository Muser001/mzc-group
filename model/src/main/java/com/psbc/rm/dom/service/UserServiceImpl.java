package com.psbc.rm.dom.service;

import com.psbc.rm.dom.dao.UserDao;
import com.psbc.rm.dom.pojo.Test_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    private String name;

    @Override
    public Integer getNumber(String name) {
        return userDao.getNumber(name);
    }

    @Override
    public void updateNumber(Test_1 test_1) {
        userDao.updateNumber(test_1);
    }

    @Override
    public void updateTest(String name) {
        this.name = name;
        final int time = 10000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Test_1 test_1 = new Test_1(this.name, 11111 ,String.valueOf(new Date()));
        userDao.updateNumber(test_1);
    }
}
