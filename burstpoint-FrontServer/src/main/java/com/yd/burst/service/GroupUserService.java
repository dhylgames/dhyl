package com.yd.burst.service;

import com.yd.burst.enums.ICode;
import com.yd.burst.model.GroupUser;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface GroupUserService {
    ICode addGroupUser(GroupUser   groupUser);
    ICode  updateGroupUser(String    groupCode);

    Object getGroupUser(String phone);

    Object findGroupUsers(GroupUser groupUser);

    ICode auditUser(String userId, String status,String groupCode);

    ICode exitGroup(String userId, String groupCode);
}
