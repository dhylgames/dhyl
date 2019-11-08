package com.yd.burst.model;

import java.util.Date;

public class AgentWeeklySettleKey {
    private String userid;

    private Date startdate;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
}