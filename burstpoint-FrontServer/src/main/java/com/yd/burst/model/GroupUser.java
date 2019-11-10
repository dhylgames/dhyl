package com.yd.burst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 群成员表
 * @author Waylon
 */
public class GroupUser extends BaseEntity{
   /**
    * 群号
    */
    private String groupCode;

    /**
     * 群成员ID
     */
    private String groupUserId;
    /**
     * 类型 0-群主 1-群成员
     */
    private String groupUserType;
    /**
     * 状态  0-待审核 1-通过 2-拒绝
     */
    private String groupUserStatus;
    /**
     * 充值虚拟币金额
     */
    private Double groupUserAmount;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(String groupUserId) {
        this.groupUserId = groupUserId;
    }

    public String getGroupUserType() {
        return groupUserType;
    }

    public void setGroupUserType(String groupUserType) {
        this.groupUserType = groupUserType;
    }

    public String getGroupUserStatus() {
        return groupUserStatus;
    }

    public void setGroupUserStatus(String groupUserStatus) {
        this.groupUserStatus = groupUserStatus;
    }

    public Double getGroupUserAmount() {
        return groupUserAmount;
    }

    public void setGroupUserAmount(Double groupUserAmount) {
        this.groupUserAmount = groupUserAmount;
    }
}