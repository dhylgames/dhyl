package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.PlayerOutPayApply;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.PlayerOutPayApplyService;
import com.yd.burst.service.WalletService;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.Constants;
import com.yd.burst.util.DateUtil;
import com.yd.burst.util.Md5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 钱包操作
 * @Author: Will
 * @Date: 2019-08-01 21:32
 **/
@Controller
@RequestMapping("api/player")
public class WalletController {

    private static Logger logger = LogManager.getLogger(PlayerController.class);

    @Autowired
    private WalletService service;

    @Autowired
    private PlayerOutPayApplyService playerOutPayApplyService;
    /**
     * 充值接口
     * @param
     * @return
     */
    @RequestMapping("/recharge")
    @ResponseBody
    public Result recharge(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        PlayerWallet player = (PlayerWallet) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (player != null) {
            Integer playerId = Integer.valueOf(params.get("playerId"));
            if (playerId.equals(player.getId())) {
                logger.info("当前充值用户ID：" + player.getId());

                String businessKey = ConfigUtil.getBusinessKey();
                String businessCode = ConfigUtil.getBusinessCode();
                String callUrl = ConfigUtil.getChargeAddress();

                StringBuffer strBuffer = new StringBuffer();
                strBuffer.append(businessCode).append(player.getId()).append(businessKey);
                String sign = Md5Util.encode(strBuffer.toString(), "UTF-8");

                Map<String, String> result = new HashMap<>(2);
                result.put("loginName", String.valueOf(player.getId()));
                result.put("businessCode", businessCode);
                result.put("sign", sign);
                result.put("callUrl", callUrl);

                return Result.success(result);
            }
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        } else {
            return Result.fail(CodeEnum.NONE_LOGIN);
        }
    }

    /**
     * 提现
     * @param request
     * @param response
     * @param params
     * @return
     */
    @RequestMapping("/withdraw")
    @ResponseBody
    public Result withDraw(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        PlayerWallet player = (PlayerWallet) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (player != null) {
            Integer playerId = Integer.valueOf(params.get("playerId"));
            if (playerId.equals(player.getId())) {
                logger.info("当前提现用户ID：" + player.getId());
                Double amount = Double.valueOf(params.get("amount"));
                if (amount <= 0d) {
                    return Result.success(CodeEnum.NO_WITHDRAY_AMOUNT);
                }
                PlayerOutPayApply apply = new PlayerOutPayApply();
                apply.setBank(Integer.valueOf(params.get("bank")));
                apply.setCardid(params.get("cardId"));
                apply.setMoney(amount);
                apply.setUserid(String.valueOf(player.getId()));
                apply.setOpname(String.valueOf(player.getId()));
                apply.setOpdate(DateUtil.dateToStr(new Date()));
                ICode code = playerOutPayApplyService.payOutApply(apply);
                if (CodeEnum.SUCCESS.getCode().equals(code.getCode())) {
                    return Result.success();
                }
                return Result.fail(code);
            }
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        } else {
            return Result.fail(CodeEnum.NONE_LOGIN);
        }
    }
}
