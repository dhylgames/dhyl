package com.yd.burst.service;

import com.yd.burst.model.Wallet;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-09 14:10
 **/
public interface WalletService {

    /**
     * 充值
     *
     * @param playerId
     * @param amount
     */
    void recharge(Integer playerId, Double amount);

    /**
     * 提现
     *
     * @param playerId
     * @param amount
     */
    Wallet withDraw(Integer playerId, Double amount);
}
