package com.yd.burst.service.impl;

import com.yd.burst.dao.GroupAmountMapper;
import com.yd.burst.model.GroupAmount;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: kelly
 * @Date: 2019/12/1 16:29
 * @Description:
 */
public class GroupAmountServiceImpl implements GroupAmountService {

    @Autowired
    private GroupAmountMapper groupAmountMapper;

    @Override
    public GroupAmount selectGroupAmount(String groupCode) {
        return groupAmountMapper.selectGroupAmount(groupCode);
    }

    @Override
    public int updateGroupAmount(GroupAmount amount) {
        return groupAmountMapper.updateGroupAmount(amount);
    }
}
