package com.yd.burst.dao;


import com.yd.burst.model.GroupInfo;

import java.util.List;

public interface GroupInfoMapper {


    int deleteGroupInfoStatus(Integer id);

    String getGroupNameById(Integer id);
}
