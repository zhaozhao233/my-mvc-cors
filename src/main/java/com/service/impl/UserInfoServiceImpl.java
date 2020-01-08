package com.service.impl;

import com.dao.UserInfoDao;
import com.entity.UserInfo;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao dao;

    @Override
    public List<UserInfo> listUserInfo(Integer pageNum, Integer pageSize) {
        return dao.listUserInfo(pageNum,pageSize);
    }

    @Override
    public int insertUserInfo(UserInfo userInfo) {
        return dao.insertUserInfo(userInfo);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return dao.updateUserInfo(userInfo);
    }

    @Override
    public int deleteUserInfo(Integer id) {
        return dao.deleteUserInfo(id);
    }
}
