package com.dao;

import com.entity.User;

public interface UserDao {

    User getUserByUsername(String uname);
}
