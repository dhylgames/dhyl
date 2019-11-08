package com.yd.burst.dao;

import com.yd.burst.model.Access2Payment;

import java.util.List;

public interface Access2PaymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Access2Payment record);

    int insertSelective(Access2Payment record);

    Access2Payment selectByOrderId(String orderId);

    int updateByPrimaryKeySelective(Access2Payment record);

    int updateByPrimaryKey(Access2Payment record);

    List<String> selectOrderId();
}
