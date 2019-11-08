package com.yd.burst.quartz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yd.burst.service.AccessPaymentService;
import com.yd.burst.service.WalletService;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.HttpClient;
import com.yd.burst.util.Md5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-05 22:45
 **/
public class PayDetailRefreshJob {

    private static Logger logger = LogManager.getLogger(PayDetailRefreshJob.class);

    private static final String payDetailAddress = ConfigUtil.getPayDetail();

    private static final String businessCode = ConfigUtil.getBusinessCode();

    private static final String businessKey = ConfigUtil.getBusinessKey();

    private static final String SUCCESS = "200";

    private static final String PAY_SUCCESS = "1";

    @Autowired
    private AccessPaymentService paymentService;

    @Autowired
    private WalletService walletService;

    public void execute() {
        Map<String, String> map = new HashMap<>(2);
        logger.debug("充值记录查询，开始执行... ...");
        try {
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(businessCode).append(businessKey);
            map.put("mercOrderIds", paymentService.getPaymentRecord());
            map.put("businessCode", businessCode);
            String ms = Md5Util.encode(strBuffer.toString(), "UTF-8");
            map.put("sign", ms);
            JSONObject jsonObj = HttpClient.httpHttpFormData(payDetailAddress, map);
            map.clear();
            if (null != jsonObj && SUCCESS.equals(jsonObj.get("code").toString())) {
                strBuffer.append(jsonObj.get("ts"));
                ms = Md5Util.encode(strBuffer.toString(), "UTF-8");
                if (!ms.equals(jsonObj.get("sign").toString())) {
                    return;
                }
                JSONArray jsonArray = JSONArray.parseArray(jsonObj.get("data").toString());
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject Obj = JSONObject.parseObject(jsonArray.get(i).toString());
                    String status = Obj.get("status").toString();
                    String playerId = Obj.get("userId").toString();
                    String amount = Obj.get("amount").toString();
                    map.put("playerId", playerId);
                    map.put("date", Obj.get("orderTime").toString());
                    map.put("orderId", Obj.get("mercOrderId").toString());
                    map.put("amount", amount);
                    map.put("status", status);
                    map.put("msg", "收银台-" + Obj.get("channelName"));
                    map.put("type", Obj.get("channelId").toString());
                    paymentService.dealPaymentRecord(map);
                    if (PAY_SUCCESS.equals(status)) {
                        walletService.recharge(Integer.valueOf(playerId), Double.valueOf(amount));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("QueryPayStatsTask Error", e);
        }
    }
}
