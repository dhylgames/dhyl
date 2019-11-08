package com.yd.burst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * @author Will
 */
public class Game  extends BaseEntity{

    @NotNull
    private Long issueNo;

    @JsonIgnore
    private String endTime;

    private Double burstPoint;

    private Double totalAmount;

    private Double playerProfit;

    private Double platformProfit;

    private Double totalBonusPool;

    private String gameMode;

    @JsonIgnore
    private String gameStatus;

    public Long getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Long issueNo) {
        this.issueNo = issueNo;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Double getBurstPoint() {
        return burstPoint;
    }

    public void setBurstPoint(Double burstPoint) {
        this.burstPoint = burstPoint;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus == null ? null : gameStatus.trim();
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPlayerProfit() {
        return playerProfit;
    }

    public void setPlayerProfit(Double playerProfit) {
        this.playerProfit = playerProfit;
    }

    public Double getPlatformProfit() {
        return platformProfit;
    }

    public void setPlatformProfit(Double platformProfit) {
        this.platformProfit = platformProfit;
    }

    public Double getTotalBonusPool() {
        return totalBonusPool;
    }

    public void setTotalBonusPool(Double totalBonusPool) {
        this.totalBonusPool = totalBonusPool;
    }
}
