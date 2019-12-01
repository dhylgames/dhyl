package com.yd.burst.service.impl;

import com.yd.burst.model.GroupAmount;

/**
 * @description: todo
 * @author: kelly
 * @create: 2019-12-01 16:28
 **/
public interface GroupAmountService {
    public GroupAmount selectGroupAmount(String groupCode);
    int updateGroupAmount(GroupAmount amount);
}
