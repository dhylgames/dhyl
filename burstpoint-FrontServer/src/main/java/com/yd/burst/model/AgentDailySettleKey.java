package com.yd.burst.model;

import java.util.Date;

public class AgentDailySettleKey {
    private Date date;

    private String userid;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}