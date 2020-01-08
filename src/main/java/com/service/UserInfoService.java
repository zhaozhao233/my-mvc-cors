package com.service;

import com.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> listUserInfo(Integer pageNum,Integer pageSize);
    // 增加用户
    int insertUserInfo(UserInfo userInfo);
    // 修改用户
    int updateUserInfo(UserInfo userInfo);
    // 删除用户
    int deleteUserInfo(Integer id);
}
