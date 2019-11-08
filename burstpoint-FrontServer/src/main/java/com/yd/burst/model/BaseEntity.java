package com.yd.burst.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @Description: 公告实体
 * @Author: Will
 * @Date: 2019-07-30 14:29
 **/
public class BaseEntity implements Serializable {

    private Integer id;
    @JsonIgnore
    private String createTime;
    @JsonIgnore
    private String createBy;
    @JsonIgnore
    private String updateTime;
    @JsonIgnore
    private String updateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

}
