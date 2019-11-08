package com.yd.burst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;

/**
 * @author Will
 */
public class BetRecord  extends BaseEntity{

    @NotNull
    private Long issueNo;

    @NotNull
    @JsonIgnore
    private Integer playerId;

    @NotNull
    private Double betAmount;

    @JsonIgnore
    private Double multiple;

    @JsonIgnore
    private String escapeTime;

    private String escapeStatus;

    private Double profit;

    @NotNull
    @JsonIgnore
    private String betStatus;

    public Long getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Long issueNo) {
        this.issueNo = issueNo;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }

    public Double getMultiple() {
        return multiple;
    }

    public void setMultiple(Double multiple) {
        this.multiple = multiple;
    }

    public String getEscapeTime() {
        return escapeTime;
    }

    public void setEscapeTime(String escapeTime) {
        this.escapeTime = escapeTime == null ? null : escapeTime.trim();
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus == null ? null : betStatus.trim();
    }

    public String getEscapeStatus() {
        return escapeStatus;
    }

    public void setEscapeStatus(String escapeStatus) {
        this.escapeStatus = escapeStatus;
    }
}