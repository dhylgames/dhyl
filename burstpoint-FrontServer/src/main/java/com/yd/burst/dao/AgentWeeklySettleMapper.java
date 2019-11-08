package com.yd.burst.dao;

import com.yd.burst.model.AgentWeeklySettle;
import com.yd.burst.model.AgentWeeklySettleKey;

public interface AgentWeeklySettleMapper {
    int deleteByPrimaryKey(AgentWeeklySettleKey key);

    int insert(AgentWeeklySettle record);

    int insertSelective(AgentWeeklySettle record);

    AgentWeeklySettle selectByPrimaryKey(AgentWeeklySettleKey key);

    int updateByPrimaryKeySelective(AgentWeeklySettle record);

    int updateByPrimaryKey(AgentWeeklySettle record);
}