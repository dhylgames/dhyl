package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.GroupInfoMapper;
import com.yd.burst.dao.GroupUserMapper;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.GroupUser;
import com.yd.burst.model.User;
import com.yd.burst.service.GroupUserService;
import com.yd.burst.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: tianyou
 * @Date: 2019-07-29 19:51
 **/
@Service
@Transactional
public class GroupUserServiceImpl implements GroupUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheBase cacheBase;

    @Autowired
    private GroupUserMapper groupUserMapper;
    @Autowired
    private GroupInfoMapper groupInfoMapper;
    @Override
    public ICode addGroupUser(GroupUser groupUser) {
        ICode code;
        int count = groupUserMapper.addGroupUser(groupUser);
        if (count > 0) {
            code = CodeEnum.SUCCESS;
        } else {
            code = CodeEnum.REGISTER_FAILED;
        }
        return code;
    }

    @Override
    public ICode updateGroupUser(String groupCode) {
        ICode code;
        code = CodeEnum.SUCCESS;
        return code;
    }

    @Override
    public Object getGroupUser(String phone) {
        User user = userMapper.selectPlayer(phone);
        if (user != null) {
            List<GroupUser> groupUsers = groupUserMapper.getGroupUser(user.getId().toString());
            if (groupUsers != null && groupUsers.size() > 0) {
                for (GroupUser  groupUser:groupUsers) {
                    groupUser.setGroupName(groupInfoMapper.getGroupNameById(Integer.parseInt(groupUser.getGroupCode())));
                    groupUser.setGroupRoom("6");
                }
                return groupUsers;
            } else {
                return CodeEnum.NOT_EXIST_PLAYER;
            }
        } else {
            return CodeEnum.NOT_EXIST_PLAYER;
        }
    }

    @Override
    public Object findGroupUsers(GroupUser groupUser) {
        List<GroupUser> groupUsers = groupUserMapper.findGroupUsers(groupUser);
        List<User> users = new ArrayList<>();
        if (groupUsers != null && groupUsers.size() > 0) {
            for (int i = 0; i < groupUsers.size(); i++) {
                User user = userMapper.selectUserById(Integer.parseInt(groupUsers.get(i).getGroupUserId()));
                users.add(user);
            }
            return users;
        } else {
            return CodeEnum.NOT_EXIST_PLAYER;
        }
    }

    @Override
    public ICode auditUser(String userId, String status,String groupCode) {
        ICode code = null;
      GroupUser  groupUser= groupUserMapper.getGroupUserById(userId,groupCode);
      try {
          if (groupUser != null) {
              groupUser.setGroupUserStatus(status);
            int  count=  groupUserMapper.updateStatus(groupUser);
            if(count>0){
                code=CodeEnum.SUCCESS;
            }else{
                 code= CodeEnum.ERROR;
            }
          } else {
              code=CodeEnum.NOT_EXIST_PLAYER;
          }
      }catch (Exception e){
          e.printStackTrace();
         code= CodeEnum.ERROR;
      }
        return code;
    }

    @Override
    public ICode exitGroup(String userId, String groupCode) {
        ICode code=null;
        try{
            int  count=  groupUserMapper.deleteGroupUserStatusByUserId(groupCode,userId);
            if(count>0){
                code= CodeEnum.SUCCESS;
            }else{
                code= CodeEnum.ERROR;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }
}

