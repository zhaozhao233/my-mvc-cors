package com.service.impl;

import com.dao.UserInfoDao;
import com.entity.UserInfo;
import config.MvcConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfig.class)
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoDao dao;

    @org.junit.Test
    public void listUserInfo() {
        System.out.println("我傻了");
        List<UserInfo> list = dao.listUserInfo(1,10);
        for (UserInfo userInfo : list) {
            System.out.println(userInfo);
        }
    }

    @org.junit.Test
    public void insertUserInfo() {
    }

    @org.junit.Test
    public void updateUserInfo() {
    }

    @org.junit.Test
    public void deleteUserInfo() {
    }
}
