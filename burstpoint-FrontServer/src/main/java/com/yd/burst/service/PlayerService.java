package com.yd.burst.service;

import com.yd.burst.enums.ICode;
import com.yd.burst.model.Player;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface PlayerService {

    /**
     * 用户注册
     * @param player
     */
    ICode register(Player player);

    /**
     * 根据用户名查找玩家
     * @param playerName
     * @return
     */
    Player findByName(String playerName);

    /**
     * 登录
     * @param playerName
     * @param password
     * @return
     */
    Object login(String playerName, String password);
}
