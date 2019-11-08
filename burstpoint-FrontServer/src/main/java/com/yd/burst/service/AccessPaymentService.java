package com.yd.burst.service;

import com.yd.burst.model.Access2Payment;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-17 15:33
 **/
public interface AccessPaymentService {

    /**
     * 添加充值记录
     */
    void dealPaymentRecord(Map<String, String> map);


    /**
     * 获取充值记录
     * @return
     */
    String getPaymentRecord();
}
