package com.yd.burst.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: kelly
 * @Date: 2019/11/13 22:57
 * @Description:
 */
public class RoomInfo implements Serializable {
    private int id;
    private int personNum;
    private int level;

    private List<User> userList;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
