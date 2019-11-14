package com.yd.burst.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户表
 * @author Waylon
 */
public class User extends BaseEntity{
    /*
     * 登录账号
     */
    private String loginName;

    /*
     * 密码
     */
    private String password;
    /*
     * 昵称
     */
    private String userName;
    /*
     * 手机号
     */
    private String phone;
    /*
     * 生日
     */
    private String birthday;
    /*
     * 用户状态 0-未激活 1-正常  2-禁用-3-删除
     */
    private String  status;
    /*
     * 性别 0-未知 1-男  2-女
     */
    private String  sex;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
