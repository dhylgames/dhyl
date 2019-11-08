package com.yd.burst.model.dto;

import java.math.BigDecimal;

/**
 * @Description: 玩家和订单dto
 * @Author: Will
 * @Date: 2019-08-07 15:02
 **/
public class GamePlayer {
    private int id;
    private String playerName;
    private Long issueNo;
    private BigDecimal capital = new BigDecimal(0);
    private BigDecimal profit = new BigDecimal(0);
    private String escapeStatus;
    private String escapteTime = null;
    private BigDecimal multiple = new BigDecimal(0);
    private String betStatus;
    private String createTime = null;
    private boolean isNpcPlayer = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Long issueNo) {
        this.issueNo = issueNo;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getEscapteTime() {
        return escapteTime;
    }

    public void setEscapteTime(String escapteTime) {
        this.escapteTime = escapteTime;
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getEscapeStatus() {
        return escapeStatus;
    }

    public void setEscapeStatus(String escapeStatus) {
        this.escapeStatus = escapeStatus;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isNpcPlayer() {
        return isNpcPlayer;
    }

    public void setNpcPlayer(boolean npcPlayer) {
        isNpcPlayer = npcPlayer;
    }
}
