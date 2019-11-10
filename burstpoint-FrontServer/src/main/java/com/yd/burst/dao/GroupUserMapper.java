package com.yd.burst.dao;


import com.yd.burst.model.GroupUser;

import java.util.List;

public interface GroupUserMapper {


    List<GroupUser> getGroupUser(String groupUserId);

    List<GroupUser> findGroupUsers(String groupCode);
}
