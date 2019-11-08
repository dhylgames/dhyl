package com.yd.burst.service;

import com.yd.burst.enums.ICode;
import com.yd.burst.model.PlayerOutPayApply;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-19 15:11
 **/
public interface PlayerOutPayApplyService {

    /**
     * 提现申请
     */
    ICode payOutApply(PlayerOutPayApply apply);

}
