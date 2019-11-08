package com.yd.burst.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:
 * bp.id,
 *         bp.player_name,
 *         bp.user_status,
 *         bw.total_money,
 *         bw.avail_money,
 *         bw.lock_money
 * @Author: Will
 * @Date: 2019-08-17 11:19
 **/
public class PlayerWallet {

    private int id;
    private String playerName;
    @JsonIgnore
    private String password;
    private String userStatus;
    private String playerType;
    private Double totalMoney;
    private Double availMoney;
    private Double lockMoney;
    private String lastLoginTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }

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

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
