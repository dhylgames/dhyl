package com.yd.burst.game;

import com.yd.burst.model.Player;
import com.yd.burst.model.Poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Auther: kelly
 * @Date: 2019/11/15 00:14
 * @Description:
 */
public class CreatPoker {
    private static final String[] colors = { "黑桃", "红桃", "梅花", "方块" };
    private static final String[] points = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
    public List<Player> CreatPoker(List<Player> list) {
        for (int i = 0; i < list.size(); i++) {
          //每个人5张牌
            for(int k=0;k<5;k++){
                int randomColor = new Random().nextInt(3);
                int randomPoint = new Random().nextInt(13);
                String color = colors[randomColor];
                String point = points[randomPoint];
                Poker poker = new Poker(color,point);
                list.get(i).setOneOfPocket(k,poker);
            }
        }
        System.out.println("send card is:"+list);
        return list;
    }

}
