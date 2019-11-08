package com.yd.burst.model.dto;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-03 14:55
 **/
public class GameAndPlayerDo implements Serializable {

    private String playerName;

    private Double multiple;

    private Double betAmount;

    private Double profit;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Double getMultiple() {
        return multiple;
    }

    public void setMultiple(Double multiple) {
        this.multiple = multiple;
    }


    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }
}
