package com.yd.burst.model;



/**
 * 群房间
 * @author Waylon
 */
public class GroupRoom extends BaseEntity{
    private int id;
   /**
    * 群号
    */
    private String groupCode;

    /**
     * 群房间类型 0-牛牛 1-金花
     */
    private String groupRoomType;
    /**
     * 状态  0-正常 1-禁用
     */
    private String groupRoomStatus;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupRoomType() {
        return groupRoomType;
    }

    public void setGroupRoomType(String groupRoomType) {
        this.groupRoomType = groupRoomType;
    }

    public String getGroupRoomStatus() {
        return groupRoomStatus;
    }

    public void setGroupRoomStatus(String groupRoomStatus) {
        this.groupRoomStatus = groupRoomStatus;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}