package com.yd.burst.dao;

import com.yd.burst.model.AgentCommissionConfig;

public interface AgentCommissionConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgentCommissionConfig record);

    int insertSelective(AgentCommissionConfig record);

    AgentCommissionConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgentCommissionConfig record);

    int updateByPrimaryKey(AgentCommissionConfig record);
}