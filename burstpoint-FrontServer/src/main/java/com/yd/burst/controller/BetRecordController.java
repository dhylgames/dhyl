package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.BetStatusEnum;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.BetRecord;
import com.yd.burst.service.BetRecordService;
import com.yd.burst.util.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:21
 **/
@RestController
@RequestMapping("api/player")
public class BetRecordController {

    private static Logger logger = LogManager.getLogger(BetRecordController.class);

    @Autowired
    private BetRecordService betRecordService;

    /**
     * 下注：玩家ID、期号、下注金额、逃跑倍率（自动时需设置、手动不需要）
     * @return
     */
    @RequestMapping("/bet")
    public Result bet(@RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        Result result;
        try {
            BetRecord record = new BetRecord();
            record.setPlayerId(Integer.valueOf(params.get("playerId")));
            String betAmount = params.get("betAmount");
            // 逃跑倍率
            String multiple = params.get("multiple");
            if (!BigDecimalUtil.isNumeric(betAmount)) {
                return Result.fail(CodeEnum.VALIDATE_FAILED);
            }
            record.setBetAmount(Double.valueOf(betAmount));
            // 下注时有逃跑倍率，说明是自动逃跑，没有则设定为手动
            if (StringUtils.isNotBlank(multiple)) {
                if (!BigDecimalUtil.isNumeric(multiple)) {
                    return Result.fail(CodeEnum.VALIDATE_FAILED);
                }
                record.setMultiple(Double.valueOf(multiple));
                record.setBetStatus(BetStatusEnum.AUTO.getCode());
            }
            record.setBetStatus(BetStatusEnum.MANUAL.getCode());
            int status = Integer.valueOf(params.get("status"));
            ICode str = betRecordService.bet(record, status);
            if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
                result = Result.success(record);
            } else {
                result = Result.fail(str);
            }
        } catch (Exception e) {
            result = Result.fail(CodeEnum.BET_FAILED);
        }
        return result;
    }

    /**
     * 逃跑
     *      手动逃跑：参数-逃跑倍数；玩家ID；当前期数
     *      自动逃跑：
     *              逃跑成功 参数-逃跑倍数；玩家ID；当前期数
     *              逃跑失败（已爆炸）不记录
     * @return
     */
    @RequestMapping("/escape")
    public Result escape(@RequestBody Map<String, String> params){
        try {
            if (params == null) {
                return Result.fail(CodeEnum.VALIDATE_FAILED);
            }
            BetRecord record = new BetRecord();
            record.setPlayerId(Integer.valueOf(params.get("playerId")));
            ICode str = betRecordService.escape(record);
            if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
                return Result.success(record);
            } else {
                return Result.fail(str);
            }
        } catch (Exception e) {
            logger.error("玩家逃跑失败" + e.getMessage());
            return Result.fail(CodeEnum.ERROR);
        }
    }

    /**
     * 单个玩家下注历史记录查询
     * @return
     */
    @RequestMapping("/history")
    public Result history(@RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        Integer playerId = Integer.valueOf(params.get("playerId"));
        List<BetRecord> betRecords = betRecordService.getAllBetRecords(playerId);
        return Result.success(betRecords);
    }
}
