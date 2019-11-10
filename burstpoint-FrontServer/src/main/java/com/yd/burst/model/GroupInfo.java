package com.yd.burst.model;

/**
 * 群信息表
 * @author Waylon
 */
public class GroupInfo extends BaseEntity{
    /**
     * 群名
     */
    private String groupName;
    /**
     * 群头像
     */
    private String groupHeadPic;
    /**
     * 群状态 0-正常 1-停用
     */
    private String groupStatus;

}