package com.yd.burst.model;

/**
 * @author Will
 */
public class Wallet  extends BaseEntity{

    private Double availMoney;

    private Double lockMoney;

    private Double totalMoney;

    private Integer playerId;

    private Double freezemoney;

    private Double overdraft;

    public Double getAvailMoney() {
        return availMoney;
    }

    public void setAvailMoney(Double availMoney) {
        this.availMoney = availMoney;
    }

    public Double getLockMoney() {
        return lockMoney;
    }

    public void setLockMoney(Double lockMoney) {
        this.lockMoney = lockMoney;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Double getFreezemoney() {
        return freezemoney;
    }

    public void setFreezemoney(Double freezemoney) {
        this.freezemoney = freezemoney;
    }

    public Double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Double overdraft) {
        this.overdraft = overdraft;
    }

}