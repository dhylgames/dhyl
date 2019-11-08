package com.yd.burst.model;

import java.util.Date;

public class AgentWeeklySettle extends AgentWeeklySettleKey {
    private Date enddate;

    private Double income;

    private Integer issettled;

    private Double minproportion;

    private Double maxproportion;

    private Double sumindirectexpense;

    private Double sumdirectexpense;

    private Integer sumfailtimes;

    private Integer sumsuccesstimes;

    private Date updatetime;

    private String updateby;

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Integer getIssettled() {
        return issettled;
    }

    public void setIssettled(Integer issettled) {
        this.issettled = issettled;
    }

    public Double getMinproportion() {
        return minproportion;
    }

    public void setMinproportion(Double minproportion) {
        this.minproportion = minproportion;
    }

    public Double getMaxproportion() {
        return maxproportion;
    }

    public void setMaxproportion(Double maxproportion) {
        this.maxproportion = maxproportion;
    }

    public Double getSumindirectexpense() {
        return sumindirectexpense;
    }

    public void setSumindirectexpense(Double sumindirectexpense) {
        this.sumindirectexpense = sumindirectexpense;
    }

    public Double getSumdirectexpense() {
        return sumdirectexpense;
    }

    public void setSumdirectexpense(Double sumdirectexpense) {
        this.sumdirectexpense = sumdirectexpense;
    }

    public Integer getSumfailtimes() {
        return sumfailtimes;
    }

    public void setSumfailtimes(Integer sumfailtimes) {
        this.sumfailtimes = sumfailtimes;
    }

    public Integer getSumsuccesstimes() {
        return sumsuccesstimes;
    }

    public void setSumsuccesstimes(Integer sumsuccesstimes) {
        this.sumsuccesstimes = sumsuccesstimes;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }
}