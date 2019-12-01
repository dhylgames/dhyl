package com.yd.burst.model;



/**
 * 战绩表
 * @author Waylon
 */
public class GameResult extends BaseEntity{
   /**
    * 群号
    */
    private String groupCode;

    /**
     * 群房间id
     */
    private String groupRoomId;
    /**
     * 群成员id
     */
    private String groupUserId;

    /**
     * 期数
     */
    private Integer issue;

    /**
     * 分数
     */
    private String result;

    private int plateNum;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupRoomId() {
        return groupRoomId;
    }

    public void setGroupRoomId(String groupRoomId) {
        this.groupRoomId = groupRoomId;
    }

    public String getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(String groupUserId) {
        this.groupUserId = groupUserId;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(int plateNum) {
        this.plateNum = plateNum;
    }
}