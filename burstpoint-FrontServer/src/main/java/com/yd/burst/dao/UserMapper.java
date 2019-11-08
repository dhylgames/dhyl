package com.yd.burst.dao;

import com.yd.burst.model.User;

import java.util.List;

public interface UserMapper {

    List<User> load();

    int insert(User player);

    User selectPlayer(String phone);


}
