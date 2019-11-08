package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.PlayerMapper;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.dao.WalletMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.enums.RoleEnum;
import com.yd.burst.enums.UserStatusEnum;
import com.yd.burst.model.Player;
import com.yd.burst.model.User;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.PlayerService;
import com.yd.burst.service.UserService;
import com.yd.burst.util.DateUtil;
import com.yd.burst.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:51
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheBase cacheBase;





    @Override
    public ICode register(User user) {
        ICode code;
        User userTemp = userMapper.selectPlayer(user.getPhone());
        if (userTemp != null) {
            return CodeEnum.EXIST_PLAYER;
        }
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        user.setCreateTime(DateUtil.dateToStr(new Date()));
        user.setUpdateTime(DateUtil.dateToStr(new Date()));
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        user.setUserType(UserStatusEnum.USER.getCode());
        int count = userMapper.insert(user);

        if (count > 0) {
            cacheBase.refreshUser(user.getLoginName());
            code = CodeEnum.SUCCESS;
        } else {
            code = CodeEnum.REGISTER_FAILED;
        }
        return code;
    }



    @Override
    public Object login(String phone, String password) {
        try {
            User user = userMapper.selectPlayer(phone);
            if (user != null) {
                if (Md5Util.getMD5(password).equals(user.getPassword())) {
                    return user;
                } else {
                    return CodeEnum.ERROR_PASSWORD;
                }
            } else {
                return CodeEnum.NOT_EXIST_PLAYER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CodeEnum.LOGIN_FAILED;
    }



    @Override
    public Object login(String phone) {
        try {
            User user = userMapper.selectPlayer(phone);
            if (user != null) {
                return user;
            } else {
                return CodeEnum.NOT_EXIST_PLAYER;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CodeEnum.LOGIN_FAILED;
    }
}

