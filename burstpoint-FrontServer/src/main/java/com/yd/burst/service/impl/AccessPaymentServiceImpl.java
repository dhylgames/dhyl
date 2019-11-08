package com.yd.burst.service.impl;

import com.yd.burst.dao.Access2PaymentMapper;
import com.yd.burst.model.Access2Payment;
import com.yd.burst.service.AccessPaymentService;
import com.yd.burst.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 *
 *
 * @Author: Will
 * @Date: 2019-08-17 15:34
 **/
@Service
@Transactional
public class AccessPaymentServiceImpl implements AccessPaymentService {

    @Autowired
    private Access2PaymentMapper paymentMapper;

    @Override
    public void dealPaymentRecord(Map<String, String> map) {
        Access2Payment payment = new Access2Payment();

        payment.setPlayerId(Integer.valueOf(map.get("playerId")));
        payment.setOrderId(map.get("orderId"));
        payment.setCreateTime(map.get("date"));
        payment.setAmount(Double.valueOf(map.get("amount")));
        payment.setStatus(Short.valueOf(map.get("status")));
        payment.setType(Short.valueOf(map.get("type")));
        payment.setMsg(map.get("msg"));
        payment.setCreateBy(map.get("playerName"));
        payment.setUpdateTime(DateUtil.dateToStr(new Date()));
        payment.setUpdateBy(map.get("playerName"));
        payment.setMode(Short.valueOf("1"));

        paymentMapper.insert(payment);
    }

    @Override
    public String getPaymentRecord() {
        List<String> list = paymentMapper.selectOrderId();
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (String str : list) {
                sb.append(str).append(",");
            }
            return sb.toString();
        } else {
            return null;
        }
    }
}
