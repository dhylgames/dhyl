package com.yd.burst.model;

/**
 * @Auther: kelly
 * @Date: 2019/12/1 16:19
 * @Description:
 */
public class GroupAmount extends BaseEntity {
    private String groupCode;
    private String groupUserId;
    private String groupUserAmount;

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

    public String getGroupUserAmount() {
        return groupUserAmount;
    }

    public void setGroupUserAmount(String groupUserAmount) {
        this.groupUserAmount = groupUserAmount;
    }
}
