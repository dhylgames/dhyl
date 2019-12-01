package com.yd.burst.dao;


import com.yd.burst.model.GameResult;
import com.yd.burst.model.GroupAmount;

import java.util.List;

public interface GroupAmountMapper {

    GroupAmount selectGroupAmount(String groupCode);

    int updateGroupAmount(GroupAmount amount);
}
