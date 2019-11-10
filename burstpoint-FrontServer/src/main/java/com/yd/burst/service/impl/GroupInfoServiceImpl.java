package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.service.GroupInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: tianyou
 * @Date: 2019-11-10 19:51
 **/
@Service
@Transactional
public class GroupInfoServiceImpl implements GroupInfoService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheBase cacheBase;

}

