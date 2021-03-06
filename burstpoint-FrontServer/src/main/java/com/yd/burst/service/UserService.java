package com.yd.burst.service;

import com.yd.burst.enums.ICode;
import com.yd.burst.model.User;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    ICode register(User user);



    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    Object login(String phone, String password);


    /**
     * 登录
     * @param phone
     * @return
     */
    Object login(String phone);


    /**
     * 密码修改
     * @param user
     */
    ICode updatePass(User user);


    /**
     * 修改用户信息
     * @param phone
     * @param sex
     *  @param userName
     */
    ICode updateByPhone(String phone,String sex,String userName);

    User selectUserById(int id);
}
