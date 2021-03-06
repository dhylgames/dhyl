package com.yd.burst.dao;


import com.yd.burst.model.GroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupUserMapper {


    List<GroupUser> getGroupUser(String groupUserId);

    List<GroupUser> findGroupUsers(GroupUser groupUser);

    int addGroupUser(GroupUser groupUser);

    GroupUser getGroupUserById(@Param("groupUserId") String groupUserId, @Param("groupCode")String groupCode);

    int updateStatus(GroupUser groupUser);

    int deleteGroupUserStatus(String groupCode);
    int deleteGroupUserStatusByUserId(@Param("groupCode")String  groupCode,@Param("groupUserId") String groupUserId);
    GroupUser getGroupUserByGroupCode(@Param("groupCode")String  groupCode);
    int updateGroupUserMoney(GroupUser groupUser);
}
