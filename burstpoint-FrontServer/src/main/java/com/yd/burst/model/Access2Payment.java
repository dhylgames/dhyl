package com.yd.burst.model;

/**
 * @author Will
 */
public class Access2Payment  extends BaseEntity{

    private String operateTime;

    private Short mode;

    private String msg;

    private Double amount;

    private Short status;

    private Short type;

    private Integer playerId;

    private String orderId;

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime == null ? null : operateTime.trim();
    }

    public Short getMode() {
        return mode;
    }

    public void setMode(Short mode) {
        this.mode = mode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
