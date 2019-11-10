package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.GroupUserMapper;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.enums.CodeEnum;
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
 * @Author: Will
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
    private GroupUserMapper   groupUserMapper;

    @Override
    public int addGroupUser(GroupUser groupUser) {
        return 0;
    }

    @Override
    public int updateGroupUser(String groupCode) {
        return 0;
    }

    @Override
    public Object getGroupUser(String phone) {
       User user= userMapper.selectPlayer(phone);
        if (user != null) {
            List<GroupUser> groupUsers= groupUserMapper.getGroupUser(user.getId().toString());
          if(groupUsers!=null&&groupUsers.size()>0){
              return groupUsers;
          }else{
              return CodeEnum.NOT_EXIST_PLAYER;
          }
        } else {
            return CodeEnum.NOT_EXIST_PLAYER;
        }
    }

    @Override
    public Object findGroupUsers(String groupCode) {
        List<GroupUser> groupUsers=  groupUserMapper.findGroupUsers(groupCode);
        List<User>  users=new ArrayList<>();
        if (groupUsers!=null&&groupUsers.size()>0){
            for (int i = 0; i <groupUsers.size() ; i++) {
              User user=  userMapper.selectUserById(Integer.parseInt(groupUsers.get(i).getGroupUserId()));
              users.add(user);
            }
            return  users;
        }else{
            return CodeEnum.NOT_EXIST_PLAYER;
        }
    }
}

