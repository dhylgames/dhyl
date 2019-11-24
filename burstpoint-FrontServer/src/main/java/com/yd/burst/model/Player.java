package com.yd.burst.model;

import java.io.Serializable;

/**
 * @Auther: kelly
 * @Date: 2019/11/14 01:41
 * @Description:
 */
public class Player implements Serializable {

    private int userId; //用户id

    private int readyState;  //准备状态

    private long waitTime; //等待时间

    private Poker[] pocket = new Poker[5]; // 玩家手上的5张牌
    private Boolean isBull = false; // 手牌是否有牛
    private int pointOfBull; // 牛几
    private Poker biggestCard; //最大点数
    private Boolean isBanker;  //是否庄家
    private long score;  //当局输赢分数

    private int IsWinAll; //通赢或通赔 0:普通  1:通赢 2:通赔


    public int getReadyState() {
        return readyState;
    }

    public void setReadyState(int readyState) {
        this.readyState = readyState;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Poker[] getPocket() {
        return pocket;
    }

    public void setPocket(Poker[] pocket) {
        this.pocket = pocket;
    }

    public void setOneOfPocket(int index, Poker poker) {
        this.pocket[index] = poker;
    }


    public Boolean getBull() {
        return isBull;
    }

    public void setBull(Boolean bull) {
        isBull = bull;
    }

    public int getPointOfBull() {
        return pointOfBull;
    }

    public void setPointOfBull(int pointOfBull) {
        this.pointOfBull = pointOfBull;
    }

    public Poker getIndexOfPoker(int index) {
        return pocket[index];
    }

    public Poker getBiggestCard() {
        return biggestCard;
    }

    public Boolean getBanker() {
        return isBanker;
    }

    public void setBanker(Boolean banker) {
        isBanker = banker;
    }

    public void setBiggestCard(Poker biggestCard) {
        this.biggestCard = biggestCard;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getIsWinAll() {
        return IsWinAll;
    }

    public void setIsWinAll(int isWinAll) {
        IsWinAll = isWinAll;
    }

    public Poker getBiggestCards(Poker[] pocket) { // 获取手牌中的最大牌:有个问题没解决，如果出现点数相同，花色不同
        Poker poker = new Poker();
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (pocket[i].getCount() > count) {
                poker = pocket[i];
                count = pocket[i].getCount();
            }
        }
        return poker;

    }

}
