package com.yd.burst.service;

import com.yd.burst.model.GroupUser;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface GroupUserService {
    int  addGroupUser(GroupUser   groupUser);
    int  updateGroupUser(String    groupCode);

    Object getGroupUser(String phone);

    Object findGroupUsers(String groupCode);
}
