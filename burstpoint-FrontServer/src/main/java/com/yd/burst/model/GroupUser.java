package com.yd.burst.model;



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
    private String groupUserAmount;

    private  String groupName;
    private String userNum;
    private String groupRoom;
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

    public String getGroupUserAmount() {
        return groupUserAmount;
    }

    public void setGroupUserAmount(String groupUserAmount) {
        this.groupUserAmount = groupUserAmount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getGroupRoom() {
        return groupRoom;
    }

    public void setGroupRoom(String groupRoom) {
        this.groupRoom = groupRoom;
    }
}