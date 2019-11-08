package com.yd.burst.dao;

import com.yd.burst.model.Mail;

public interface MailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Mail record);

    int insertSelective(Mail record);

    Mail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mail record);

    int updateByPrimaryKey(Mail record);
}