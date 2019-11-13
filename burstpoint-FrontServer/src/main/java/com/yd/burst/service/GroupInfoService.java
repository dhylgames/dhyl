package com.yd.burst.service;


import com.yd.burst.enums.ICode;
import com.yd.burst.model.RoomInfo;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface GroupInfoService {


    ICode disBandGroup(String groupCode);

    String getGroupName(String groupCode);

    List<RoomInfo> getGroupRoomInfo(String groupCode);
}
