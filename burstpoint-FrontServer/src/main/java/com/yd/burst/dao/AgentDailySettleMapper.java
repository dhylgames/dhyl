package com.yd.burst.dao;

import com.yd.burst.model.AgentDailySettle;
import com.yd.burst.model.AgentDailySettleKey;

public interface AgentDailySettleMapper {
    int deleteByPrimaryKey(AgentDailySettleKey key);

    int insert(AgentDailySettle record);

    int insertSelective(AgentDailySettle record);

    AgentDailySettle selectByPrimaryKey(AgentDailySettleKey key);

    int updateByPrimaryKeySelective(AgentDailySettle record);

    int updateByPrimaryKey(AgentDailySettle record);
}