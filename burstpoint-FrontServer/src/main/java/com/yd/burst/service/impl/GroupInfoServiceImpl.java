package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.GroupInfoMapper;
import com.yd.burst.dao.GroupUserMapper;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.RoomInfo;
import com.yd.burst.service.GroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: tianyou
 * @Date: 2019-11-10 19:51
 **/
@Service
@Transactional
public class GroupInfoServiceImpl implements GroupInfoService {

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private CacheBase cacheBase;

    @Autowired
    private GroupInfoMapper groupInfoMapper;

    @Override
    public ICode disBandGroup(String groupCode) {
        ICode code=null;
        try{
          int  count=  groupInfoMapper.deleteGroupInfoStatus(Integer.parseInt(groupCode));
          if(count>0){
          int  count1=    groupUserMapper.deleteGroupUserStatus(groupCode);
          if(count1>0){
              code= CodeEnum.SUCCESS;
          }else{
              code= CodeEnum.ERROR;
          }
          }else{
            code= CodeEnum.ERROR;
          }
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }

    @Override
    public String getGroupName(String groupCode) {
       String  groupName= groupInfoMapper.getGroupNameById(Integer.parseInt(groupCode));
        return groupName;
    }

    @Override
    public List<RoomInfo> getGroupRoomInfo(String groupCode) {
        return cacheBase.getORoomInfo(groupCode);
    }
}

