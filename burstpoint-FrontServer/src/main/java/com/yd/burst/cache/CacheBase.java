package com.yd.burst.cache;

import com.yd.burst.dao.*;
import com.yd.burst.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-05 15:48
 **/
@Service
public class CacheBase {



    @Autowired
    private RedisPool redisPool;


    @Autowired
    private UserMapper userMapper;


    /**
     * 刷新用户
     * @param key
     */
    public void refreshUser(String key) {
        User user = userMapper.selectPlayer(key);
        redisPool.setData4Object2Redis(CacheKey.USER_KEY + key, user);
    }

    /**
     * 获取群房间信息
     * @param key
     * @return
     */
    public List<GroupRoom> getORoomInfo(String key){
        return (List<GroupRoom>) redisPool.getData4Object2Redis(key);
    }





}
