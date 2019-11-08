package com.yd.burst.dao;

import com.yd.burst.model.Config;

import java.util.List;

public interface ConfigMapper {

    Config selectByKey(String key);

    List<Config> selectAllConfig();
}