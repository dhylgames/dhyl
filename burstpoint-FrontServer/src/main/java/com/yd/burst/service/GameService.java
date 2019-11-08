package com.yd.burst.service;

import com.yd.burst.model.Game;
import com.yd.burst.model.dto.GameDto;
import com.yd.burst.model.dto.GamePlayer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:28
 **/
public interface GameService {

    /**
     * 分页获取爆点数据
     * @return
     */
    List<Game> getAllBurstPoints();

    /**
     * 创建新游戏（每一局结束时，修改当前局的结束时间并记录爆点，同时创建下一局游戏）
     */
    Game createNewGame();


    /**
     * 达到爆炸点，游戏结束
     */
    boolean checkBurstPoint();

    /**
     * 计算增长点
     * @return
     */
    void calcIncreasePoint();


    /**
     * 到达爆点之后的处理
     * @param game
     */
    void dealAfterBurst(Game game);


    /**
     * 获取200期的开奖历史并进行分析
     * @param multiple
     * @return
     */
    Map<String, Object> getLotteryHistory(String multiple);




}
