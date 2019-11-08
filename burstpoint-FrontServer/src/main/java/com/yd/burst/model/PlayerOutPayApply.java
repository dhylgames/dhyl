package com.yd.burst.model;

public class PlayerOutPayApply {
    private Integer id;

    private Integer bank;

    private String cardid;

    private String operateTime;

    private String errorreason;

    private Double money;

    private String name;

    private String opdate;

    private String opname;

    private Integer status;

    private String userid;

    private String certiftype;

    private String certifid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid == null ? null : cardid.trim();
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime == null ? null : operateTime.trim();
    }

    public String getErrorreason() {
        return errorreason;
    }

    public void setErrorreason(String errorreason) {
        this.errorreason = errorreason == null ? null : errorreason.trim();
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate == null ? null : opdate.trim();
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname == null ? null : opname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getCertiftype() {
        return certiftype;
    }

    public void setCertiftype(String certiftype) {
        this.certiftype = certiftype == null ? null : certiftype.trim();
    }

    public String getCertifid() {
        return certifid;
    }

    public void setCertifid(String certifid) {
        this.certifid = certifid == null ? null : certifid.trim();
    }

}
