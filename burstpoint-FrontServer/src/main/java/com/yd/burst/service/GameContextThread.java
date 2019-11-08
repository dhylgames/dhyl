package com.yd.burst.service;

import com.yd.burst.model.Game;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.Constants;
import com.yd.burst.util.GameContext;
import com.yd.burst.util.SpringContextHolder;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-09 18:31
 **/
public class GameContextThread implements Runnable {

    private static Logger logger = LogManager.getLogger(GameContextThread.class);

    @Override
    public void run() {
        cerateGame();
        boolean isOk = true;
        int i = 1;
        while (isOk) {
            long startTime = System.currentTimeMillis();
            long endTime = 0L;
            try {
                //logger.debug("----------开始游戏----------");
                Thread.sleep(ConfigUtil.getCalcPointInterval());
                endTime = System.currentTimeMillis();
                logger.debug(String.format("期号[%s]第%d轮耗时%d毫秒----1", GameContext.GAME_CONTEXT.getIssueNo(), i, (endTime - startTime)));
                GameContext.GAME_CONTEXT.setStatus(Constants.GAMING);
                GameContext.GAME_CONTEXT.increase();
                logger.debug(String.format("期号[%s]第%d轮耗时%d毫秒----2", GameContext.GAME_CONTEXT.getIssueNo(), i, (endTime - startTime)));
                GameContext.GAME_CONTEXT.autoEscape();
                logger.debug(String.format("期号[%s]第%d轮耗时%d毫秒----3", GameContext.GAME_CONTEXT.getIssueNo(), i, (endTime - startTime)));
            } catch (InterruptedException e) {
                logger.error("游戏错误", e);
            }
            if (SpringContextHolder.getBean(GameService.class).checkBurstPoint()) {
                GameContext.GAME_CONTEXT.setStatus(Constants.GAMEOVER);
                GameContext.GAME_CONTEXT.dealIsBurstPoint();
                cerateGame();
                logger.debug("未检查到爆点");
            }
            endTime = System.currentTimeMillis();
            logger.debug(String.format("期号[%s]第%d轮耗时%d毫秒----4", GameContext.GAME_CONTEXT.getIssueNo(), i, (endTime - startTime)));
            i++;
        }
    }

    private void cerateGame() {
        logger.debug("----------初始化参数----------");
        GameContext.GAME_CONTEXT.setStatus(Constants.OTHER);

        logger.debug("----------创建游戏----------");
        Game game = SpringContextHolder.getBean(GameService.class).createNewGame();
        logger.debug("游戏期号：" + game.getIssueNo());
        float countdown = 6.0f;
        while (countdown > 0) {
            try {
                Thread.sleep(100);
                if (RandomUtils.nextFloat(0.1f, 6.0f) > countdown * 2) {
                    GameContext.GAME_CONTEXT.increaseNpcPlayer();
                }
                GameContext.GAME_CONTEXT.setTime(countdown);
                GameContext.GAME_CONTEXT.setStatus(Constants.WAIT_START);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countdown -= 0.1;
        }
    }

}
