package com.yd.burst.model;

import java.io.Serializable;

/**
 * @Auther: kelly
 * @Date: 2019/11/13 22:57
 * @Description:
 */
public class RoomInfo implements Serializable {
    private int index;
    private int personNum;
    private int level;

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
}
