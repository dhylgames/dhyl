package com.yd.burst.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: kelly
 * @Date: 2019/11/13 22:57
 * @Description:
 */
public class RoomInfo implements Serializable {
    private int index;
    private int personNum;
    private int level;

    private List<Player> player;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Player> getPlayer() {
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }
}
