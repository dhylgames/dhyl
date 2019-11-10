package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.service.GroupRoomService;
import com.yd.burst.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:51
 **/
@Service
@Transactional
public class GroupRoomServiceImpl implements GroupRoomService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheBase cacheBase;

}

