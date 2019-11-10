package com.yd.burst.dao;

import com.yd.burst.model.User;

import java.util.List;

public interface UserMapper {

    List<User> load();

    int insert(User user);

    User selectPlayer(String phone);

    int updatePassByPhone(User user);

    int updateByPhone(User user);


    User selectUserById(int id);
}
