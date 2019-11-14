package com.yd.burst.game;

import com.yd.burst.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: kelly
 * @Date: 2019/11/14 23:47
 * @Description:
 */
public class NnCompare {

    // 判断手牌手否有牛
    public boolean isBull(Player player) { // 判断手牌手否有牛
        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                for (int k = j + 1; k < 5; k++) {
                    if ((player.getIndexOfPoker(i).getPoints()
                            + player.getIndexOfPoker(j).getPoints()
                            + player.getIndexOfPoker(k).getPoints()) % 10 == 0) {
                        //  player.setBull(true); // 如果玩家手中的任意三张牌之和是10的倍数，则有牛
                        return  true;
                    }
                }
            }
        }
        return false;
    }

    // 判断手牌为牛几
    public int pointOfBull(Player player) { // 判断手牌为牛几
        int allPoints = 0; // 五张牌的总点数
        for (int i = 0; i < 5; i++) {
            allPoints += player.getIndexOfPoker(i).getPoints();
        }
        return allPoints % 10;
    }

    //
    public List<Player> compare(List<Player> list) {
        int winNum = 0;
        List<Player> newList = new ArrayList<>();
        //把不是庄家的玩家和庄家进行比较，然后计算得分
        Player banker = null;
        List<Player> noBankerList = new ArrayList<>();
        for (Player player : list) {
            if (player.getBanker()) {
                banker = player;
            } else {
                noBankerList.add(player);
            }
        }
        //把不是庄家的玩家和庄家进行比较，然后计算得分
        for (Player player : noBankerList) {
            if (banker.getBull() && player.getBull()) {
                if (banker.getPointOfBull() > player.getPointOfBull()) {
                    // 如果庄家牛大，则赢得分
                    banker.setScore(banker.getScore() + player.getScore());
                    player.setScore(-player.getScore());
                    winNum ++;
                } else if (banker.getPointOfBull() < player.getPointOfBull()) {
                    banker.setScore(-player.getScore());
                    winNum --;
                    // player.setScore(-player.getScore());
                } else {                                   // 如果牛的点数相同，则比较最大牌
                    if (banker.getBiggestCard().getCount() > player.getBiggestCard().getCount()) {
                        banker.setScore(banker.getScore() + player.getScore());
                        player.setScore(-player.getScore());
                        winNum ++;
                    } else {
                        banker.setScore(-player.getScore());
                        winNum --;
                    }
                }
            } else if (banker.getBull() && !player.getBull()) {
                banker.setScore(banker.getScore() + player.getScore());
                player.setScore(-player.getScore());
                winNum ++;
            } else if (!banker.getBull() && player.getBull()) {
                banker.setScore(-player.getScore());
                winNum --;
            } else if (!banker.getBull() && !player.getBull()) {
                //都无牛的情况
            }
            newList.add(player);
        }
        if(winNum==list.size()-1){
            banker.setWinAll(true);
            banker.setFailAll(false);
        }else if(winNum==-(list.size()-1)){
            banker.setFailAll(true);
            banker.setWinAll(false);
        }
        newList.add(banker);
        return newList;
    }

}
