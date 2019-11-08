package com.yd.burst.service;

import com.yd.burst.enums.ICode;
import com.yd.burst.model.BetRecord;
import com.yd.burst.model.dto.GameAndPlayerDo;
import com.yd.burst.model.dto.GamePlayer;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:22
 **/
public interface BetRecordService {

    /**
     * 下注接口
     * @param record
     */
    ICode bet(BetRecord record, int status);

    /**
     * 逃跑接口
     * @param record
     * @return
     */
    ICode escape(BetRecord record);

    /**
     * 获取当前用户所有下注订单
     * @param playerId
     * @return
     */
    List<BetRecord> getAllBetRecords(Integer playerId);

    /**
     * 获取一笔订单
     * @param record
     * @return
     */
    BetRecord getBetRecord(BetRecord record);

    /**
     * 根据期数获取当前局游戏情况
     * @param issueNo
     * @return
     */
    List<GameAndPlayerDo> getCurrentGameData(Long issueNo);

    /**
     * 处理爆炸后的逻辑：注单、游戏和钱包更新
     * @param list
     */
    void dealAfterBurstPoint(List<GamePlayer> list);

    /**
     * 处理下一局下注
     * @param list
     */
    void dealNextGameBet(List<GamePlayer> list);


}
