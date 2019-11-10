package com.yd.burst.dao;


import com.yd.burst.model.GroupInfo;

import java.util.List;

public interface GroupInfoMapper {
    int addGroupInfo(GroupInfo  groupInfo);
    int  updateGroupInfo(GroupInfo  groupInfo);
    List<GroupInfo>  findGroupInfos();
    List<GroupInfo> findGroupsByGroupUserId(String groupUserId);
}
