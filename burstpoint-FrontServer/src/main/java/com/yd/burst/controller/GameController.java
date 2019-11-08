package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.model.Game;
import com.yd.burst.model.dto.GameAndPlayerDo;
import com.yd.burst.model.dto.GameDto;
import com.yd.burst.service.BetRecordService;
import com.yd.burst.service.GameService;
import com.yd.burst.util.BigDecimalUtil;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.GameContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:53
 **/
@RestController
@RequestMapping("api/game")
public class GameController {

    private static Logger logger = LogManager.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private BetRecordService betRecordService;


    /**
     * 分页获取所有爆点数据
     *
     * @return
     */
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public Result getAllPoints() {
        List<Game> games = gameService.getAllBurstPoints();
        return Result.success(games);
    }

    /**
     * 开奖历史计算
     *
     * @return
     */
    @RequestMapping(value = "/lottery")
    public Result getAllLotteryHistory(@RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String multiple = params.get("multiple");
        Map map = gameService.getLotteryHistory(multiple);
        return Result.success(map);
    }


    /**
     * 获取当前期玩家和投注情况
     *
     * @return
     */
    @RequestMapping("/current")
    public Result getCurrentData() {
        Long issueNo = GameContext.GAME_CONTEXT.getIssueNo();
        List<GameAndPlayerDo> resultList = betRecordService.getCurrentGameData(issueNo);
        return Result.success(resultList);
    }

    /**
     * 最大投注额：总奖池 * 0.0075 / 1.5
     *
     * @return
     */
    @RequestMapping("/max")
    public Result getMax() {
        BigDecimal decimal = new BigDecimal(ConfigUtil.getTotalAmount());
        BigDecimal totalAmountWeight = new BigDecimal(ConfigUtil.getTotalAmountWeight());
        BigDecimal totalAmountParam = new BigDecimal(ConfigUtil.getTotalAmountParam());
        decimal = decimal.multiply(totalAmountWeight).divide(totalAmountParam, 2).setScale(4, BigDecimal.ROUND_HALF_UP);
        Map<String, BigDecimal> map = new HashMap<>(2);
        map.put("max", decimal);
        return Result.success(map);
    }

    /**
     * 最小投注额
     *
     * @return
     */
    @RequestMapping("/min")
    public Result getMin() {
        BigDecimal decimal = new BigDecimal(ConfigUtil.getMinAmount());
        Double result = decimal.doubleValue();
        Map<String, Double> map = new HashMap<>(2);
        map.put("mix", result);
        return Result.success(map);
    }

    /**
     * 最大收益倍数 = 总奖池 * 0.75% / 自己的投注
     *
     * @return
     */
    @RequestMapping("/multiple")
    public Result getMultiple(@RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String betAmount = params.get("betAmount");
        if (!BigDecimalUtil.isNumeric(betAmount)) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        Map<String, BigDecimal> map = new HashMap<>(2);
        if (betAmount.equals("0")) {
            map.put("multiple", BigDecimal.ZERO);
        } else {
            BigDecimal decimal = new BigDecimal(ConfigUtil.getTotalAmount());
            BigDecimal totalAmountWeight = new BigDecimal(ConfigUtil.getTotalAmountWeight());
            decimal = decimal.multiply(totalAmountWeight).divide(new BigDecimal(betAmount), 2).setScale(4, BigDecimal.ROUND_HALF_UP);
            map.put("multiple", decimal);
        }
        return Result.success(map);
    }


}
