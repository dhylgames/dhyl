package com.yd.burst.model;

import java.util.Date;

public class AgentDailySettle extends AgentDailySettleKey {
    private Double selfincome;

    private Double sumincome;

    private Double directexpense;

    private Double indirectexpense;

    private Double proportion;

    private Integer failtimes;

    private Integer successtimes;

    private Date updatetime;

    private String updateby;

    public Double getSelfincome() {
        return selfincome;
    }

    public void setSelfincome(Double selfincome) {
        this.selfincome = selfincome;
    }

    public Double getSumincome() {
        return sumincome;
    }

    public void setSumincome(Double sumincome) {
        this.sumincome = sumincome;
    }

    public Double getDirectexpense() {
        return directexpense;
    }

    public void setDirectexpense(Double directexpense) {
        this.directexpense = directexpense;
    }

    public Double getIndirectexpense() {
        return indirectexpense;
    }

    public void setIndirectexpense(Double indirectexpense) {
        this.indirectexpense = indirectexpense;
    }

    public Double getProportion() {
        return proportion;
    }

    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    public Integer getFailtimes() {
        return failtimes;
    }

    public void setFailtimes(Integer failtimes) {
        this.failtimes = failtimes;
    }

    public Integer getSuccesstimes() {
        return successtimes;
    }

    public void setSuccesstimes(Integer successtimes) {
        this.successtimes = successtimes;
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