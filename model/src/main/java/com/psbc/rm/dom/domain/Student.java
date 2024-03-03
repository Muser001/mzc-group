package com.psbc.rm.dom.domain;

import com.psbc.rm.dom.dao.UserDao;
import com.psbc.rm.dom.pojo.Test_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Student {

    @Autowired
    private UserDao userDao;


    public void doNewTxfun() {
//        Test_1 test_1 = new Test_1("张三",333);
//        userDao.updateNumber(test_1);
//        System.out.println(1/0);
//        Test_1 test_2 = new Test_1("张四",444);
//        userDao.updateNumber(test_2);
        Test_1 test_1 = new Test_1("张三",333);
        userDao.updateNumber(test_1);
        System.out.println(1/0);
        Test_1 test_2 = new Test_1("张四",444);
        userDao.updateNumber(test_2);
    }

    public void fun6() {
        Test_1 test_1 = new Test_1("张六",666);
        userDao.updateNumber(test_1);
    }

    public void fun5() {
        Test_1 test_1 = new Test_1("张五",555);
        userDao.updateNumber(test_1);
    }

    public void doNewTxTest() {
        fun6();
    }

}
