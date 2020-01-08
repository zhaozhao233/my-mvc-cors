package com.dao;

import com.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDao {
    // 查询所有用户
    List<UserInfo> listUserInfo(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);
    // 增加用户
    int insertUserInfo(@Param("user") UserInfo userInfo);
    // 修改用户
    int updateUserInfo(@Param("user") UserInfo userInfo);
    // 删除用户
    int deleteUserInfo(@Param("id") Integer id);
}
